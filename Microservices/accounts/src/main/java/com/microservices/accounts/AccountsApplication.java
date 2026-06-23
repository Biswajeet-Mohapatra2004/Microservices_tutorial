package com.microservices.accounts;

import com.microservices.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//if we are using no fixed structure for the application and manually
// want to load the main class and enable loading of entities and controller we
// can use certain annotation.This helps in situation where the controller, entities etc
// are not inside subpackages of main application package.


//@ComponentScan({"com.microservices.accounts.controller"})
//@EnableJpaRepositories({"com.microservices.accounts.repository"})
//@EntityScan({"com.microservices.accounts.model"})
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // enable the listener in the application by passing the bean name of the auditAware implementation
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice rest api documentation",
				description = "A simple microservice application for banking",
				version = "v1",
				contact = @Contact(
						name = "Biswajeet Mohapatra",
						email = "mbiswajeet66@gmail.com",
						url = "biswajeetmohapatra.vercel.app"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https:apache.org"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Eazybank account microservice REST API documentation",
				url = "http://localhost:8080/swagger-ui/index.html"
		)
)
@EnableConfigurationProperties(value = AccountsContactInfoDto.class) // enable the configuration properties class to be loaded in the application context
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
