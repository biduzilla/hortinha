package com.ricky.hortinha

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
	info = Info(
		title = " REST API Hortinha",
		description = "Documentação da REST API Hortinha",
		version = "v1",
		contact = Contact(
			name = "Luiz Henrique",
			email = "luiz.devs@gmail.com",
		),
		license = License(
			name = "Apache 2.0",
		)
	),
	externalDocs = ExternalDocumentation(
		description = "Documentação da REST API Hortinha",
	)
)
class HortinhaApplication

fun main(args: Array<String>) {
	runApplication<HortinhaApplication>(*args)
}
