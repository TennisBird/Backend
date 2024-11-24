package org.tennis_bird;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TennisBirdBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(TennisBirdBackendApplication.class, args);
    }
}