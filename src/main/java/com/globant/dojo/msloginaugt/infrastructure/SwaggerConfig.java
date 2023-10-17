package com.globant.dojo.msloginaugt.infrastructure;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class SwaggerConfig {

	private final SwaggerProperties props;

	@Bean
	public OpenAPI getOpenApi() {
		return new OpenAPI().info(getApiInfo());
	}

	private Info getApiInfo() {
		return new Info()
				.title(props.getTitle())
				.description(props.getDescription())
				.version(props.getVersion())
				.license(getApiLicence())
				.contact(getApiContact())
				.termsOfService(props.getTerms());
	}

	private License getApiLicence() {
		return new License()
				.name(props.getLicence())
				.url(props.getLicenceUrl());
	}

	private Contact getApiContact() {
		return new Contact()
				.name(props.getContact().getName())
				.url(props.getContact().getUrl())
				.email(props.getContact().getEmail());
	}

}