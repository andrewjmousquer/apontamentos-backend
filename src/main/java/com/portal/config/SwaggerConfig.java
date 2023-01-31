package com.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Portal Carbon Consulting", version = "1.0.0"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", description = "Segurança feita por bearer Token, solicite o mesmo no caminho '/auth' com seu usuário e senha ")
public class SwaggerConfig {

	@Bean
	public OpenAPI CustomOpenApiConfig() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI().components(new Components())
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
	}

}

