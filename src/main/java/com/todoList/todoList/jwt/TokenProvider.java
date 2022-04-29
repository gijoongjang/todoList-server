package com.todoList.todoList.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.token-validity-in-seconds}")
    long tokenValidityInMiliseconds;

    public String createToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("auth", userDetails.getAuthorities().stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority())
                        .collect(Collectors.joining(",")))
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(System.currentTimeMillis() + (tokenValidityInMiliseconds * 1000)))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }
}
