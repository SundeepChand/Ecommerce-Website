package com.example.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi
                .builder()
                .group("ecommerce")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI ecommerceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce API")
                        .description("Documentation of Ecommerce API")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/license/LICENSE-2.0"))
                );
    }
}
