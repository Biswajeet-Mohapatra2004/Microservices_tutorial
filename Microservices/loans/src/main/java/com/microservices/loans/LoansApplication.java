package com.microservices.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservices rest api documentations",
				description = "A simple microservice application for banking",
				version = "v1",
				contact = @Contact(
						name = "Biswajeet Mohapatra",
						email = "mbiswajeet66@gmail.com",
						url = "biswajeetmohapatra.vercel.app"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://apache.org"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EazyBank loans microservice REST API documentations",
				url = "http://localhost:8090/swagger-ui/index.html"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
