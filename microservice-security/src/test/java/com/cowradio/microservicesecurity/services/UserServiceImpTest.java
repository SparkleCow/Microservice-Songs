package com.cowradio.microservicesecurity.services;

import com.cowradio.microservicesecurity.entities.Role;
import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.Set;

class UserServiceImpTest {

    @InjectMocks
    private UserServiceImp userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_return_user_with_username(){
        //Given
        User userTest = new User(1L,"SparkleCowTest", "sparklecito@outlook.es",
                "123456", Set.of(Role.USER), Set.of());

        //Mock calls
        Mockito.when(userRepository.findByUsername("SparkleCowTest")).thenReturn(Optional.of(userTest));

        //When
        User user = userService.findByUsername("SparkleCowTest");

        //Then
        Assertions.assertEquals(user.getUsername(), userTest.getUsername());
        Assertions.assertEquals(user.getEmail(), userTest.getEmail());
    }
}