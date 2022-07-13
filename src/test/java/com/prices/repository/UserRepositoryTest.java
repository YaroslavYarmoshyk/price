package com.prices.repository;

import com.prices.config.TestPostgresContainer;
import com.prices.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Unit tests for UserRepository")
class UserRepositoryTest extends TestPostgresContainer {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("/scripts/init_two_users.sql")
    @DisplayName("Test get user by id")
    void getUserById() {
        User actual = userRepository.getById(2L);
        User expected = new User();
        expected.setId(2L);
        expected.setEmail("bob@i.ua");
        expected.setPassword("3233");
        expected.setRoles(Set.of());

        assertEquals(expected, actual);
    }
}