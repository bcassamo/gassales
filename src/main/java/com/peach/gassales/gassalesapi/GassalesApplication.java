package com.peach.gassales.gassalesapi;

import com.peach.gassales.gassalesapi.config.property.GassalesProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties(GassalesProperty.class)
public class GassalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GassalesApplication.class, args);
    }

}
