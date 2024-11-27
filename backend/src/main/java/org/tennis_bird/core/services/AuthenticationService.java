package org.tennis_bird.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.tennis_bird.api.data.InfoConverter;
import org.tennis_bird.api.data.JetResponse;
import org.tennis_bird.api.data.PersonInfoRequest;
import org.tennis_bird.api.data.SignInRequest;
import org.tennis_bird.core.entities.PersonEntity;

@Component
public class AuthenticationService {
    @Autowired
    PersonService personService;

    @Autowired
    JwtService jwtService;

    @Autowired
    InfoConverter converter;

    public JetResponse signUp(PersonInfoRequest request) {
        String token = jwtService.generateToken(converter.requestToEntity(request));
        JetResponse response = new JetResponse(token);

        // this thing returns some info
        personService.create(converter.requestToEntity(request));

        return response;
    }

    public JetResponse signIn(SignInRequest request) throws Exception {
        PersonEntity user = personService.findByLogin(request.getLogin());
        var jwt = jwtService.generateToken(user);
        return new JetResponse(jwt);
    }
}
