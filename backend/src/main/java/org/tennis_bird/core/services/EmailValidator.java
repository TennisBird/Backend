package org.tennis_bird.core.services;

import com.sanctionco.jmail.JMail;
import org.springframework.stereotype.Service;

@Service
public class EmailValidator {
    public boolean isValid(String email) {
        return JMail.isValid(email);
    }
}
