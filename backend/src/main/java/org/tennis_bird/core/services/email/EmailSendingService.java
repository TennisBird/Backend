/*
package org.tennis_bird.core.services.email;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class EmailSendingService {

    @Autowired
    EmailCreatorService emailCreatorService;

    @Value("${google.gmail.apiKey}")
    private final static String GMAIL_API_KEY = "";

    @Value("${google.gmail.fromEmail}")
    private final static String GMAIL_FROM_EMAIL = "";

    private GoogleCredentials credentials;
a
    private Gmail gmailService;

    public EmailSendingService() {
        credentials = GoogleCredentials.create(AccessToken.newBuilder().setTokenValue(GMAIL_API_KEY).build());
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        gmailService = new Gmail.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("tennisbird")
                .build();
    }

    public ResponseEntity<Message> sendEmail(String toEmail, String subject, String messageBody) {
        try {
            Message message = emailCreatorService.createMessage(GMAIL_FROM_EMAIL, toEmail, subject, messageBody);
            message = gmailService.users().messages().send("me", message).execute();
            return ResponseEntity.ok(message);
        } catch (MessagingException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }
}
*/
