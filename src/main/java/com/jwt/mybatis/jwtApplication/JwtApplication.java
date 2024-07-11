package com.jwt.mybatis.jwtApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "JWT Token Generation",
                version = "1.0.0",
                description = "This project is used to generate JWT Token",
                termsOfService = "Copyright@2023",
                contact = @Contact(
                        name = "Vaibhav",
                        email = "vaibhav@gmail.com"
                ),
                license = @License(
                        name = "license",
                        url = "something.google.com"
                )
        )
)
@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

}
