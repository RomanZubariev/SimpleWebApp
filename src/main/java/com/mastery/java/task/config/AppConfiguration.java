package com.mastery.java.task.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
      title = "Simple Web App",
      description = "REST API that performs CRUD operations on Employee objects.",
      version = "0.0.1-SNAPSHOT"
    )
)
public class AppConfiguration {

}
