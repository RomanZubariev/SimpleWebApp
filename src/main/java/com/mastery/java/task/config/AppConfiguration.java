package com.mastery.java.task.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Value("${app.name}")
  private String appName;
  @Value("${app.version}")
  private String appVersion;
  @Value("${app.description}")
  private String appDescription;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info(
        new Info().title(appName).description(appDescription).version(appVersion));
  }
}
