package com.mastery.java.task.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mastery.java.task.rest.GlobalExceptionHandler;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import javax.jms.ConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
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

  @Bean
  public ActiveMQConnectionFactoryCustomizer configureRedeliveryPolicy() {
    return connectionFactory -> {
      RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
      redeliveryPolicy.setMaximumRedeliveries(0);
      connectionFactory.setRedeliveryPolicy(redeliveryPolicy);
    };
  }

  @Bean
  public DefaultJmsListenerContainerFactory containerFactory(ConnectionFactory connectionFactory,
      DefaultJmsListenerContainerFactoryConfigurer configurer,
      GlobalExceptionHandler globalExceptionHandler) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setErrorHandler(globalExceptionHandler);
    configurer.configure(factory, connectionFactory);
    return factory;
  }

  @Bean
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setObjectMapper(new ObjectMapper().registerModule(new JavaTimeModule()));
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("Employee");
    return converter;
  }
}
