package com.multree.userservice.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.multree.userservice.entity.UserRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
public class UserRegistrationRequestTest {

    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        log.info("@BeforeEach - executes before each test method in this class");
    }


    @Test
    @DisplayName("Single test successful")
    void testConstructorAndGetters() {
        // Arrange
        String organizationName = "Example Org";
        String email = "user@example.com";
        String phoneNumber = "1234567890";
        String password = "password123";

        // Act
        UserRegistrationRequest request = new UserRegistrationRequest(
                organizationName,
                email,
                phoneNumber,
                password);

        // Assert
        assertEquals(organizationName, request.organization_name());
        assertEquals(email, request.email());
        assertEquals(phoneNumber, request.phone_number());
        assertEquals(password, request.password());
    }

    @Test
    @DisplayName("test if String")
    void testToString() {
        // Arrange
        String organizationName = "Example Org";
        String email = "user@example.com";
        String phoneNumber = "1234567890";
        String password = "password123";
        UserRegistrationRequest request = new UserRegistrationRequest(
                organizationName,
                email,
                phoneNumber,
                password);

        // Act
        String result = request.toString();

        // Assert
        String expected = "UserRegistrationRequest[organization_name=" + organizationName +
                ", email=" + email +
                ", phone_number=" + phoneNumber +
                ", password=" + password + "]";
        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Test the registration request")
    public void testUserRegistrationRequest() {

        UserRegistrationRequest request = new UserRegistrationRequest(
                "Multree",
                "test@example.com",
                "1234567890",
                "password123");

        assertEquals("Multree", request.organization_name());
        assertEquals("test@example.com", request.email());
        assertEquals("1234567890", request.phone_number());
        assertEquals("password123", request.password());
    }

    @Test
    @DisplayName("Test equals and hash code")
    void testEqualsAndHashCode() {
        // Arrange
        String organizationName1 = "Example Org";
        String email1 = "user@example.com";
        String phoneNumber1 = "1234567890";
        String password1 = "password123";
        UserRegistrationRequest request1 = new UserRegistrationRequest(
                organizationName1,
                email1,
                phoneNumber1,
                password1);

        String organizationName2 = "Example Org";
        String email2 = "user@example.com";
        String phoneNumber2 = "1234567890";
        String password2 = "password123";
        UserRegistrationRequest request2 = new UserRegistrationRequest(
                organizationName2,
                email2,
                phoneNumber2,
                password2);

        String organizationName3 = "Another Org";
        String email3 = "user2@example.com";
        String phoneNumber3 = "0987654321";
        String password3 = "qwerty";
        UserRegistrationRequest request3 = new UserRegistrationRequest(
                organizationName3,
                email3,
                phoneNumber3,
                password3);

        // Act & Assert
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertEquals(request1, request1);
        assertEquals(request1.hashCode(), request1.hashCode());
        assertEquals(request1.equals(request3), false);
        assertEquals(request1.hashCode() == request3.hashCode(), false);
        assertEquals(request1.equals(null), false);
        assertEquals(request1.equals(new Object()), false);
    }

    @Test
    @DisplayName("test static mockito")
    public void testMockitoStatic() {
        try (MockedStatic<UserRegistrationRequest> mockedRequest = Mockito.mockStatic(UserRegistrationRequest.class)) {
            mockedRequest.when(() -> UserRegistrationRequest.of("Multree", "test@example.com", "1234567890", "password123"))
                    .thenReturn(new UserRegistrationRequest("Multree", "test@example.com", "1234567890", "password123"));

            UserRegistrationRequest request = UserRegistrationRequest.of("Multree", "test@example.com", "1234567890", "password123");

            assertEquals("Multree", request.organization_name());
            assertEquals("test@example.com", request.email());
            assertEquals("1234567890", request.phone_number());
            assertEquals("password123", request.password());
        }
    }
}

