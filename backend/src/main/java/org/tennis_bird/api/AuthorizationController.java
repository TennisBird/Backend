package org.tennis_bird.api;

import java.util.Optional;
import java.util.UUID;

import javax.security.auth.login.CredentialException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;/*
import org.tennis_bird.core.services.email.EmailSendingService;
*/
import org.tennis_bird.api.data.JwtResponse;
import org.tennis_bird.api.data.PersonInfoRequest;
import org.tennis_bird.api.data.SignInRequest;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.services.AuthenticationService;
import org.tennis_bird.core.services.PersonService;

//todo refactor? move to separated microservice(it may not worth it)
@RestController
public class AuthorizationController {
    private static final Logger logger = LogManager.getLogger(AuthorizationController.class.getName());

    @Autowired
    private PersonService personService;

    @Autowired
    AuthenticationService authenticationService;

/*
    @Autowired
    EmailSendingService emailSendingService;
*/

    // Todo implement
    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    ResponseEntity<JwtResponse> registerNewUser(@RequestBody PersonInfoRequest request) {
        logger.info("Got registration request: " + request.toString());
        try {
            JwtResponse response = authenticationService.signUp(request);
            String toEmail = request.getMailAddress();
/*            emailSendingService.sendEmail(toEmail, "Registartion", "To complete registartion, click the next link: "
                    + "https://wayzapnet.duckdns.org:20345/api/auth/email-verification/");*/
            return ResponseEntity.ok().body(response);
        } catch (CredentialException exception) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // TODO proper exception handling
    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    ResponseEntity<JwtResponse> authenticatePerson(@RequestBody SignInRequest request) {
        logger.info("Got authetication request: " + request.toString());
        JwtResponse response = null;
        try {
            if (request.getEmail() != null && request.getLogin() != null) {
                return ResponseEntity.badRequest().body(null);
            }
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

    //TODO change return type
    @GetMapping(path = "/email-verification/{uuid}", produces = "aplicatuon/json")
    ResponseEntity<String> verifyEmail(@PathVariable("uuid") UUID uuid) {
        Optional<PersonEntity> person = personService.verifyUser(uuid);
        if (person.isPresent()) {
            return ResponseEntity.ok().body("Email verified");
        }
        return ResponseEntity.badRequest().body(null);
    }
}
