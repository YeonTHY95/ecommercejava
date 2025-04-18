package com.example.yeon.ecommercejava.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    Logger jwtAFLogger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authentionHeader = request.getHeader("Authorization");
        String accessToken = null;
        String refreshToken = null;
        String username = null;

        String path = request.getRequestURI();
        jwtAFLogger.info("Request URI: " + request.getRequestURI());
        if (path.startsWith("/api/login") || path.startsWith("/api/refresh")) {
            jwtAFLogger.info("Inside doFilterInternal, the path is path.equals to /api/refresh ? Answer : " + path.startsWith("/api/refresh") );
            filterChain.doFilter(request, response);
            return;
        }

        jwtAFLogger.info("Inside doFilterInternal Request URL is " + request.getRequestURI());
//        if (authentionHeader == null || !authentionHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

//        if (authentionHeader != null && authentionHeader.startsWith("Bearer ")) {
//            token = authentionHeader.substring(7);
//            username = jwtService.extractUsername(token);
//        }

        // Extract JWT from Cookie
        if (request.getCookies() != null) {
            jwtAFLogger.info("Inside JWT doFilterInternal getCookies()");
            //Cookie[] cookieList = Arrays.stream(request.getCookies()) ;//List.of(request.getCookies());
            //jwtAFLogger.info("cookieList is " + cookieList);
//            Cookie accessTokenCookie = Arrays.stream(request.getCookies())
//                    .filter(c -> c.getName().equals("jwtCookie"))
//                    .findFirst().orElseGet(()-> null);
//            accessToken = accessTokenCookie.getValue();
////                    .map(Cookie::getValue)
////                    .orElseGet(()-> null);
//            //accessToken = cookieList.stream().filter(c -> c.getName() == "jwtCookie").findFirst().map(Cookie::getValue).orElse(null);
//            jwtAFLogger.info("accessToken is " + accessToken);
//            username = jwtService.extractUsername(accessToken);

//            String accessToken = Arrays.stream(request.getCookies())
//                    .filter(c -> c.getName().equals("accessToken"))
//                    .findFirst()
//                    .map(Cookie::getValue)
//                    .orElse(null);
            for (Cookie cookie : request.getCookies()) {
                jwtAFLogger.info("Inside looping request.getCookies");
                jwtAFLogger.info("cookie.getName() is " + cookie.getName());
                if ("jwtCookie".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    jwtAFLogger.info("accessToken is " + accessToken);
                    username = jwtService.extractUsername(accessToken, false);
                    jwtAFLogger.info("username is " + username);
                }
            }
        }

        if ( username != null & SecurityContextHolder.getContext().getAuthentication() == null) {
            jwtAFLogger.info("inside getAuthentication");
            UserDetails userDetails = applicationContext.getBean(CustomUserDetailsService.class).loadUserByUsername(username);
//            List<Cookie> cookieList = List.of(request.getCookies());
//            refreshToken = cookieList.stream().filter(c -> c.getName() == "jwtCookieForRefresh").findFirst().map(Cookie::getValue).orElse(null);
            refreshToken = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("jwtCookieForRefresh"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElseGet(()-> null);
            jwtAFLogger.info("refreshToken is " + refreshToken);
            if (jwtService.validateToken(accessToken, userDetails, jwtService.getAccessKey())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            else if (jwtService.validateToken(refreshToken, userDetails,jwtService.getRefreshKey())){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);

    }
}
