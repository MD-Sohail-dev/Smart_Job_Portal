package com.mdsohail.smartjobportal.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Project information
                .info(new Info()
                        .title("Smart Job Portal APIs")
                        .description(
                                "This is a complete API documentation for the Smart Job Portal project developed by MD Sohail Ansari.\n\n" +
                                        "Project Purpose:\n" +
                                        "- To allow users to sign up, login, view jobs, and apply for jobs.\n" +
                                        "- To enable Google OAuth2 login for faster authentication.\n" +
                                        "- To provide admin features like posting jobs and viewing all users.\n\n" +
                                        "Key Features:\n" +
                                        "1. User Registration and Authentication (JWT-based security)\n" +
                                        "2. Google OAuth2 Login Integration\n" +
                                        "3. CRUD operations for users and jobs\n" +
                                        "4. Email notifications on job application\n" +
                                        "5. Secure endpoints using Spring Security\n\n" +
                                        "Usage:\n" +
                                        "- Use this documentation to explore APIs, test endpoints, and understand request/response structures.\n" +
                                        "- JWT token required for protected endpoints like /user/** and /admin/**."
                        )
                        .version("1.0")
                        .contact(new Contact()
                                .name("MD Sohail Ansari")
                                .email("mdsohail@example.com")
                                .url("https://github.com/mdsohail"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                // Server configuration
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8081").description("Local Development Server")
                ))
                // JWT Bearer Authentication configuration
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        ));
    }
}
