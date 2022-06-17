package com.mastery.java.task.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Configuration
public class AppConfiguration {

  @Bean
  SimpleJdbcInsert simpleJdbcInsert(DataSource dataSource){
    return new SimpleJdbcInsert(dataSource);
  }
}
