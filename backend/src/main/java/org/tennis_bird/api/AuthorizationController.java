package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tennis_bird.api.data.JwtRepsonse;
import org.tennis_bird.api.data.PersonInfoRequest;
import org.tennis_bird.api.data.SignInRequest;
import org.tennis_bird.core.services.AuthenticationService;
import org.tennis_bird.core.services.PersonService;

//todo refactor? move to separated microservice(it may not worth it)
@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    private static final Logger logger = LogManager.getLogger(AuthorizationController.class.getName());

    @Autowired
    private PersonService personService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    ResponseEntity<JwtRepsonse> registerNewUser(@RequestBody PersonInfoRequest request) {
        logger.info("Got registration request: " + request.toString());
        JwtRepsonse response = authenticationService.signUp(request);
        return ResponseEntity.ok().body(response);
    }

    // TODO proper exception handling
    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    ResponseEntity<JwtRepsonse> authenticatePerson(@RequestBody SignInRequest request) {
        logger.info("Got authetication request: " + request.toString());
        JwtRepsonse response = null;
        try {
            if (request.getEmail() != null) {
                String login = personService.findByEmail(request.getEmail()).getLogin();
                request.setLogin(login);
            }
            response = authenticationService.signIn(request);

        } catch (Exception e) {
            logger.error("error: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(response);
    }
}
