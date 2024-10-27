package org.tennis_bird.core.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tennis_bird.core.services.PersonService;

//better to use bean that create SecurityFilterChain
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // TODO delete logger
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class.getName());
/* 
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter; */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MessageDigestPasswordEncoder("MD5");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.warn("Used Custom Security configuration");
        // TODO implement csrf securing(for now it can be skipped)
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(requests -> {
                    requests.antMatchers("/api/auth/register", "/api/auth/login")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(new PersonService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}