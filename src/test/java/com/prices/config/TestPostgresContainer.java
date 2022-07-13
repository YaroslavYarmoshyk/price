package com.prices.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestPostgresContainer {
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("springboot")
            .withPassword("springboot")
            .withUsername("springboot")
            .withReuse(true);

    @DynamicPropertySource
    static void setDataSourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @BeforeAll
    public static void setUp() {
        database.start();
    }

    @AfterAll
    static void afterAll() {
        database.close();
    }
}