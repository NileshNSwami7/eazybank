package com.eazybytes.loans;

import com.eazybytes.loans.audit.AuditAwareImpl;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef="auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title="Loans microservice REST API Documentation.",
                description="eazyBank Loans microservices REST API Documentation",
                version="v1",
                contact=@Contact(
                        name="Nilesh N. Swami",
                        email="nilnswami110@gmail.com",
                        url=""
                ),
                license=@License(
                        name="Apache 2.0",
                        url="https://"
                )
        ),
        externalDocs=@ExternalDocumentation(
                description="eazyBank Loans Microservices REST API Documentation",
                url="http://localhost:8090/swagger-ui/index.html"
        )
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
