package org.tennis_bird.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tennis_bird.api.data.InfoConverter;
import org.tennis_bird.api.data.JwtResponse;
import org.tennis_bird.api.data.PersonInfoRequest;
import org.tennis_bird.api.data.SignInRequest;
import org.tennis_bird.core.entities.PersonEntity;

import javax.security.auth.login.CredentialException;

@Component
public class AuthenticationService {
    @Autowired
    PersonService personService;

    @Autowired
    JwtService jwtService;

    @Autowired
    InfoConverter converter;

    public JwtResponse signUp(PersonInfoRequest request) throws CredentialException{
        PersonEntity entity = converter.requestToEntity(request);
        PersonEntity user = personService.create(converter.requestToEntity(request));
        String token = jwtService.generateToken(entity);
        JwtResponse response = new JwtResponse(token, user.getUuid());

        if(user == null){
            throw new CredentialException("Email address is invalid");
        };

        return response;
    }

    public JwtResponse signIn(SignInRequest request) throws Exception {
        PersonEntity user = personService.findByLogin(request.getLogin());
        var jwt = jwtService.generateToken(user);
        return new JwtResponse(jwt, user.getUuid());
    }
}
