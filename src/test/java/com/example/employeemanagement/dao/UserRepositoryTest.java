package com.example.employeemanagement.dao;

import com.example.employeemanagement.dao.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenNotExistsUsername_shouldNotFindUser() {
        User user = userRepository.findByUsername("dummy@dummy.com");
        assertNull(user);
    }

    @Test
    public void givenNotToken_shouldNotFindUser() {
        User user = userRepository.findByToken(UUID.randomUUID().toString());
        assertNull(user);
    }

    @Test
    public void shouldFindUserByUsername() {
        User user = mockUser();
        userRepository.save(user);
        user = userRepository.findByUsername(user.getUsername());
        assertNotNull(user);
    }

    @Test
    public void shouldFindUserByToken() {
        User user = mockUser();
        userRepository.save(user);
        user = userRepository.findByToken(user.getToken());
        assertNotNull(user);
    }

    private User mockUser() {
        User user = new User();
        user.setUsername("dummy@dummy.com");
        user.setPassword("P@ssw0rd");
        user.setToken(UUID.randomUUID().toString());
        return user;
    }

}