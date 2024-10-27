package org.tennis_bird.core.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tennis_bird.api.data.PersonInfoRequest;
import org.tennis_bird.core.entities.PersonEntity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtService {

    private static final long TOKEN_EXPIRE_TIME = 60 * 60 * 1000;

//    @Value("${jwt.secret}")
    private String jwtSecret = "ajhguygb788& ^U!UI!G YCSTYGUH!SJSP{|!}@!HNC";

    // TODO PersonRequest instead of PersonEntity?
    public String generateToken(PersonEntity request) {

        return JWT.create()
                .withSubject(request.getMailAddress())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
                .withClaim("password", request.getPassword()) 
                .sign(Algorithm.HMAC512(jwtSecret.getBytes()));
    }

    public String extractEmailFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean isTokenValid(String token, PersonEntity request) {
        final String email = extractEmailFromToken(token);
        return (email.equals(request.getMailAddress()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecret.getBytes()))
                .build()
                .verify(token)
                .getExpiresAt()
                .before(new Date());
    }
}