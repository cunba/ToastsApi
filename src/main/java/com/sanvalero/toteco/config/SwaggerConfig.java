package com.sanvalero.toteco.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    private final String TITLE = "Toteco Service";

    private final String DESCRIPTION = "Toteco API";

    private final String BASE_PACKAGE = "com.sanvalero.toteco.controller";

    @Bean
    public GroupedOpenApi totecoOpenApi() {
        return GroupedOpenApi.builder()
                .group("TotecoApi")
                .packagesToScan(BASE_PACKAGE + ".toteco")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Irene Cunto").email("ire.cunba@gmail.com")));
    }
}
