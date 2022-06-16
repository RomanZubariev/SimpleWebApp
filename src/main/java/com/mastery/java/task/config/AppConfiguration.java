package com.mastery.java.task.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Configuration
@ComponentScan("com.mastery.java.task")
public class AppConfiguration {

  @Bean
  DataSource dataSource() {
    DataSourceBuilder dsBuilder = DataSourceBuilder.create();
    dsBuilder.url("jdbc:postgresql://localhost:5432/employeedb");
    dsBuilder.username("postgres");
    dsBuilder.password("postgres");
    return dsBuilder.build();
  }

  @Bean
  JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  SimpleJdbcInsert simpleJdbcInsert() {
    return new SimpleJdbcInsert(dataSource());
  }
}
