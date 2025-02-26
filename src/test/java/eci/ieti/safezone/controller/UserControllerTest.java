package eci.ieti.safezone.controller;

import eci.ieti.safezone.model.User;
import eci.ieti.safezone.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId("1");
        user1.setName("Alice");
        user1.setEmail("alice@example.com");
        user1.setPhoneNumber("1234567890");
        user1.setCity("New York");
        user1.setPassword("password123");
        user1.setDocumentType(User.DocumentType.CC);
        user1.setDocumentNumber("100200300");
        user1.setProfile("Admin");

        user2 = new User();
        user2.setId("2");
        user2.setName("Bob");
        user2.setEmail("bob@example.com");
        user2.setPhoneNumber("0987654321");
        user2.setCity("Los Angeles");
        user2.setPassword("securepass");
        user2.setDocumentType(User.DocumentType.TI);
        user2.setDocumentNumber("400500600");
        user2.setProfile("User");
    }

    @Test
    void getUsers_ShouldReturnListOfUsers() {
        List<User> users = Arrays.asList(user1, user2);
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getUsers();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userService.findById("1")).thenReturn(Optional.of(user1));

        ResponseEntity<User> response = userController.getUserById("1");

        assertEquals(OK, response.getStatusCode());
        assertEquals(user1, response.getBody());
        verify(userService, times(1)).findById("1");
    }

    @Test
    void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        when(userService.findById("3")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById("3");

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).findById("3");
    }

    @Test
    void saveUser_ShouldReturnCreatedStatus() {

        ResponseEntity<User> response = userController.saveUser(user1);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals(user1, response.getBody());
        verify(userService, times(1)).save(user1);
    }

    @Test
    void deleteUser_ShouldReturnNoContentStatus() {
        doNothing().when(userService).deleteUserById("1");

        ResponseEntity<?> response = userController.deleteUser("1");

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUserById("1");
    }
}