package io.nottodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NottodoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(NottodoApplication.class, args);
    }
    
}
