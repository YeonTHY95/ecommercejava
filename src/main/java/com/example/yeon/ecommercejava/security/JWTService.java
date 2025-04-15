package com.example.yeon.ecommercejava.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String secretKey = "8kDuhzNm8i9VsLgCkxT1Qan8m4gNpBlReK3L0A6IVkYczCHUmPxrhW5wUc2n0LQE";
    private Integer jwtExpirationInMs = 10 * 60 * 1000; // 10 minutes

    Map<String, Object> claims = new HashMap<>();

    public JWTService() {
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = Keys.secretKeyFor(SignatureAlgorithm.HS512); //keyGenerator.generateKey();
            this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        JWTServiceLogger.info("Generating Token");
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .and()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

//    public String getUsernameFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseSignedClaims(token)
//                .getBody()
//                .getSubject();
//    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Claims extractAllClaims (String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}
