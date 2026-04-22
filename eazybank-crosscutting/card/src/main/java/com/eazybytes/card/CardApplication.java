package com.eazybytes.card;

import com.eazybytes.card.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
@EnableConfigurationProperties(value= CardsContactInfoDto.class)
@OpenAPIDefinition(
        info = @Info(
                title="Cards microservice REST API Documentation.",
                description="eazyBank Cards microservices REST API Documentation",
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
                description="eazyBank Cards Microservices REST API Documentation",
                url="http://localhost:9000/swagger-ui/index.html"
        )
)
public class CardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardApplication.class, args);
	}

}
