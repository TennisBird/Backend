package org.tennis_bird.core.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.services.JwtService;
import org.tennis_bird.core.services.PersonService;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final PersonService personService;
    @Autowired
    private final JwtService jwtService;

    public JwtAuthenticationFilter(PersonService personService, JwtService jwtService) {
        this.personService = personService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String login;
        AntPathMatcher pathMatcher = new AntPathMatcher();

        if (request.getRequestURI().equals("/api/auth/register") ||
                request.getRequestURI().equals("/api/auth/login") ||
                pathMatcher.match("/h2-console/**", request.getRequestURI())
            //  || pathMatcher.match("**", request.getContextPath())//TODO now need for chat
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        jwt = authHeader.substring(7);
        login = jwtService.extractLogin(jwt);

        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            PersonEntity userDetails = this.personService.loadUserByUsername(login);
            if (!jwtService.isTokenValid(jwt, userDetails)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }
}