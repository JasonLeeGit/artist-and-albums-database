package com.ltd.coders.software.artist.and.albums.database;

import org.springdoc.core.models.GroupedOpenApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	 GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("Artist And Albums")
				.pathsToMatch("/v3/api-docs/**", 
						"/swagger-ui/**", 
						"/v1/artist/service/initialize/database", 
						"/v1/artist/service/artist*/**")
				.build();
	}
	
	@Bean
	OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Artist And Albums API").description("Lists all Artists and or Albums")
						.version("v0.0.1").license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("SpringShop Wiki Documentation")
						.url("https://springshop.wiki.github.org/docs"));
	}
}
