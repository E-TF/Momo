package com.project.momo;

import com.project.momo.event.LockNameVerifyEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MomoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MomoApplication.class);
        app.addListeners(new LockNameVerifyEvent());
        app.run(args);
    }

}
