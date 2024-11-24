package org.tennis_bird.core.services;

import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.tennis_bird.core.entities.PersonEntity;

import java.util.Date;

@Service
public class JwtService {

    private static final long TOKEN_EXPIRE_TIME = 60 * 60 * 1000;

   // @Value("${jwt.secret}")
    private String jwtSecret = "ijbasgdvahsdhsbahdbyubetuwinqegbyqunihyg 123xuwheubgyqwiuenyqwe";


    public String generateToken(PersonEntity userDetails) {
        String login = userDetails.getLogin();
        return JWT.create()
                .withSubject(login)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC256(jwtSecret.getBytes()));
    }

    public String extractLogin(String token) {
        try {
            String login = JWT.require(Algorithm.HMAC256(jwtSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            return login;
        } catch (Exception e) {
            return null; 
        }
    }

    public boolean isTokenValid(String token, PersonEntity userDetails) { // Use UserDetails
        final String login = extractLogin(token);
        return (login != null && login.equals(userDetails.getLogin()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        try {
             return JWT.require(Algorithm.HMAC256(jwtSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getExpiresAt()
                    .before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}