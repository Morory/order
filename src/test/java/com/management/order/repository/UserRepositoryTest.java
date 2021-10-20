package com.management.order.repository;

import com.management.order.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
public class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test
    void save() {
        final User user = User.builder()
                .username("testName")
                .email("testEmail@email.com")
                .password("testPassword")
                .build();

        final User savedUser = userRepository.save(user);

        assertEquals(user.getUsername(), savedUser.getUsername());
    }
}
