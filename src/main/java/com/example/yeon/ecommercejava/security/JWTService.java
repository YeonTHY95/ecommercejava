package com.example.yeon.ecommercejava.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {


    Logger JWTServiceLogger = LoggerFactory.getLogger(JWTService.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.refresh-secret}")
    private String secretKeyForRefreshToken;


    private Integer jwtExpirationInMs = 15000 ; //10 * 1000 ; //10 * 60 * 1000; // 10 minutes

    Map<String, Object> claims = new HashMap<>();

//    public JWTService() {
//        try{
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = Keys.secretKeyFor(SignatureAlgorithm.HS512); //keyGenerator.generateKey();
//            SecretKey skR = Keys.secretKeyFor(SignatureAlgorithm.HS512); //keyGenerator.generateKey();
//            this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//            this.secretKeyForRefreshToken = Base64.getEncoder().encodeToString(skR.getEncoded());
//        }
//        catch (NoSuchAlgorithmException e){
//            throw new RuntimeException(e);
//        }
//    }

    public String generateAccessToken(String username) {
        JWTServiceLogger.info("Generating access Token");
        JWTServiceLogger.info("secretKey is " + this.secretKey);
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + jwtExpirationInMs);

        JWTServiceLogger.info("IssuedAt: " + now);
        JWTServiceLogger.info("ExpiresAt: " + exp);
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .and()
                .signWith( SignatureAlgorithm.HS512,getAccessKey())
                .compact();
    }

    public String generateRefreshToken(String username) {
        JWTServiceLogger.info("Generating refresh Token");
        JWTServiceLogger.info("secretKeyForRefreshToken is " + this.secretKey);
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 )) // 24 hours
                .and()
                .signWith(SignatureAlgorithm.HS512,getRefreshKey())
                .compact();
    }

//    public String getUsernameFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseSignedClaims(token)
//                .getBody()
//                .getSubject();
//    }

    private SecretKey getSignInKey(String sk) {
        byte[] keyBytes = Decoders.BASE64.decode(sk);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, SecretKey key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token, boolean isRefreshToken) {
        SecretKey key = isRefreshToken ? getRefreshKey() : getAccessKey();
        return extractClaim(token, Claims::getSubject, key);
    }

    public Date extractExpiration(String token, boolean isRefreshToken) {
        SecretKey key = isRefreshToken ? getRefreshKey() : getAccessKey();
        return extractClaim(token, Claims::getExpiration, key);
    }

    private boolean isTokenExpired(String token, boolean isRefreshToken) {
        SecretKey key = isRefreshToken ? getRefreshKey() : getAccessKey();
        return extractExpiration(token, isRefreshToken).before(new Date());
    }

    private Claims extractAllClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails, SecretKey key) {
        try {
            JWTServiceLogger.info("Inside JWTService's ValidateToken");
            JWTServiceLogger.info("Secret Key is " + key);
            JWTServiceLogger.info("getRefreshKey() " + getRefreshKey());
            boolean isRefresh = false ;
            if ( key.equals(getRefreshKey())) {
                isRefresh = true ;
            }
            final String username = extractClaim(token, Claims::getSubject, key);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token, isRefresh);
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public SecretKey getAccessKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public SecretKey getRefreshKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKeyForRefreshToken);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
