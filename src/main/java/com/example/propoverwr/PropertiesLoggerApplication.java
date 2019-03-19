package com.example.propoverwr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PropertiesLoggerApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(PropertiesLoggerApplication.class);
        springApplication.addListeners(new PropertiesLogger());
        springApplication.run(args);
    }

}
