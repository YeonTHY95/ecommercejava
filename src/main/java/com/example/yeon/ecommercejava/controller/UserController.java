package com.example.yeon.ecommercejava.controller;


import com.example.yeon.ecommercejava.dto.*;
import com.example.yeon.ecommercejava.entity.UserEntity;
import com.example.yeon.ecommercejava.security.CustomUserDetailsService;
import com.example.yeon.ecommercejava.security.JWTService;
import com.example.yeon.ecommercejava.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    Logger userLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    JWTService jwtService;

    @Autowired
    ModelMapper modelMapper;

    @ResponseBody
    @PostMapping(path = "/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        userLogger.info("Inside Login API");
        String token = userService.verifyUser(loginDTO);
        if (token.equals( "Failed JWT Authentication")) {
            userLogger.info("Failed JWT Authentication");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        userLogger.info("After JWT Authentication, getting UserID");
        Long userId = userService.getUserId(loginDTO);
        userLogger.info("userId is " + userId);

        // Add Cookie
        Cookie cookie = new Cookie("jwtCookie", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // set to '/' so it's accessible to all endpoints
        cookie.setMaxAge(24 * 60 * 60); // 1 day

        // For Refresh Token
        Cookie cookieForRefreshToken = new Cookie("jwtCookieForRefresh", jwtService.generateRefreshToken(loginDTO.getUsername()));
        cookieForRefreshToken.setPath("/");
        cookieForRefreshToken.setHttpOnly(true);
        cookieForRefreshToken.setMaxAge(3 * 24 * 60 * 60); // 3 day

        userLogger.info("Access Token is " + token);
        userLogger.info("Refresh Token is " + jwtService.generateRefreshToken(loginDTO.getUsername()));
        response.addCookie(cookie);
        response.addCookie(cookieForRefreshToken);

        LoginResponse loginResponse = new LoginResponse(token,jwtService.extractExpiration(token, false), new UserResponseDTO(String.valueOf(userId)));
        userLogger.info("loginResponse is " + loginResponse);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(path = "/api/userinfo")
    public ResponseEntity<UserDTO> getUserRole(@RequestParam("id") Long userId) {
        userLogger.info("Inside getUserRole API");
        UserEntity userEntity = userService.getUserEntity(userId);
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(path = "api/signup")
    public ResponseEntity<Object> signup(@RequestBody UserDTO userDTO) {
        if (userService.signup(userDTO) == "User Signup Successfully")  {
            return new ResponseEntity<>("User Signup Successfully", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(new ResponseErrorDTO(userService.signup(userDTO)), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value={"/api/refresh", "/api/refresh/"}, produces = "application/json")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userLogger.info("Inside API Refresh");
        String refreshToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("jwtCookieForRefresh")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing refresh token");
            return;
        }

//        String username = jwtService.extractUsername(refreshToken, true);
        SecretKey refreshKey = jwtService.getRefreshKey();
        userLogger.info("refreshKey is " + refreshKey);
        Claims claims = Jwts.parser()
                .verifyWith(refreshKey)
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();

        String username = claims.getSubject();
        userLogger.info("username from claims.getSubject is " + username);

        UserDetails userDetails = applicationContext.getBean(CustomUserDetailsService.class).loadUserByUsername(username);

        userLogger.info("userDetails from applicationContext.getBean is " + userDetails);

        if (refreshToken != null && jwtService.validateToken(refreshToken, userDetails, jwtService.getRefreshKey())) {
            userLogger.info("Inside Validate Refresh Token");
            username = jwtService.extractUsername(refreshToken, true);
            // reissue new access token
            userLogger.info("username from jwtService.extractUsername is " + username);
            String newAccessToken = jwtService.generateAccessToken(username);
            userLogger.info("newAccessToken is " + newAccessToken);
            Cookie newAccessCookie = new Cookie("jwtCookie", newAccessToken);
            newAccessCookie.setHttpOnly(true);
//            newAccessCookie.setSecure(true);
            newAccessCookie.setPath("/");
            newAccessCookie.setMaxAge(24 * 60 * 60); // 1 day
            response.addCookie(newAccessCookie);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Access token refreshed\"}");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid refresh token");
        }
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwtCookie", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete the cookie for access token

        Cookie cookieForRefresh = new Cookie("jwtCookieForRefresh", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete the cookie for refresh token

        response.addCookie(cookie);
        response.addCookie(cookieForRefresh);

        return ResponseEntity.ok("Logged out successfully");
    }
}
