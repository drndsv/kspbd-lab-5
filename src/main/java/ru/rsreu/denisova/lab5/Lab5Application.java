package ru.rsreu.denisova.lab5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class Lab5Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab5Application.class, args);
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5433/rrtu")
                .username("postgres")
                .password("postgres")
                .build();
    }
}
