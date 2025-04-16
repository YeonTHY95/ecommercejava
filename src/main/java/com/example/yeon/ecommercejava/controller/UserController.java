package com.example.yeon.ecommercejava.controller;


import com.example.yeon.ecommercejava.dto.*;
import com.example.yeon.ecommercejava.entity.UserEntity;
import com.example.yeon.ecommercejava.security.JWTService;
import com.example.yeon.ecommercejava.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    Logger userLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @Autowired
    ModelMapper modelMapper;

    @ResponseBody
    @PostMapping(path = "/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        userLogger.info("Inside Login API");
        String token = userService.verifyUser(loginDTO);
        if (token == "Failed JWT Authentication") {
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

        response.addCookie(cookie);

        LoginResponse loginResponse = new LoginResponse(token,jwtService.extractExpiration(token), new UserResponseDTO(String.valueOf(userId)));
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


    @PostMapping("/api/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwtCookie", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete the cookie

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}
