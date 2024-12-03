package org.tennis_bird.core.services.email;

import com.sanctionco.jmail.JMail;
import org.springframework.stereotype.Service;

@Service
public class EmailValidationService {
    public boolean isValid(String email) {
        return JMail.isValid(email);
    }
}
