package com.project.momo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MomoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MomoApplication.class)
                .properties("spring.config.location=" +
                        "classpath:/application.yml," +
                        "classpath:/secrets.yml," +
                        "classpath:/datasource.yml")
                .run(args);
    }

}
