package org.tennis_bird.core.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.tennis_bird.api.PersonsController;
import org.tennis_bird.api.data.InfoConverter;
import org.tennis_bird.api.data.JwtRepsonse;
import org.tennis_bird.api.data.PersonInfoRequest;
import org.tennis_bird.api.data.SignInRequest;
import org.tennis_bird.core.entities.PersonEntity;

@Component
public class AuthenticationService {
    @Autowired
    PersonsController controller;

    @Autowired
    PersonService personService;

    @Autowired
    JwtService jwtService;

    @Autowired
    InfoConverter converter;

    @Autowired
    AuthenticationManager authenticationManager;

    public JwtRepsonse signUp(PersonInfoRequest request) {
        // TODO refactor, a lot of overhead and conversions
        String token = jwtService.generateToken(converter.requestToEntity(request));
        JwtRepsonse response = new JwtRepsonse(token);

        // this thing returns some info
        controller.createPerson(request);

        return response;
    }

    public JwtRepsonse signIn(SignInRequest request) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()));

        Optional<PersonEntity> user = personService.findByEmail(request.getEmail());

        if (user.isPresent()) {
            var jwt = jwtService.generateToken(user.get());
            return new JwtRepsonse(jwt);
        }

        // TODO better implementation
        throw new Exception("USer doesnt not exists");
    }
}
