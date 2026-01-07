package com.vima.vnshiptrackbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class VnshipTrackBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(VnshipTrackBotApplication.class, args);
    }

}
