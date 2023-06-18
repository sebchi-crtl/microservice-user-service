package com.multree.userservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.multree.userservice.entity.RoleUser;
import com.multree.userservice.entity.User;
import com.multree.userservice.entity.UserRegistrationRequest;
import com.multree.userservice.exception.UserAuthenticationConflictException;
import com.multree.userservice.exception.UserEmailNotValidException;
import com.multree.userservice.repository.UserRepository;
import com.multree.userservice.response.UserRegistrationResponse;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
@SpringBootTest
public class UserServiceImplTest {


    @Autowired
    private Environment env;

    @Value("${insert}")
    private String INSERT_SUCCESS;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }


    @Test
    void testMyProperty() {
        log.info(INSERT_SUCCESS);
        String expectedValue = "Data successfully stored in the database";
        String actualValue = env.getProperty("insert");
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Test findAllCustomers()")
    public void testFindAllCustomers() {
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setEmail("test1@test.com");
        user1.setOrganization_name("testOrg1");
        user1.setRole(Collections.singleton(RoleUser.USER));
        user1.setActive(true);
        user1.setVerify(false);
        user1.setPhone_number("1234567890");
        user1.setCreatedAt(LocalDateTime.now());
        userList.add(user1);

        User user2 = new User();
        user2.setEmail("test2@test.com");
        user2.setOrganization_name("testOrg2");
        user2.setRole(Collections.singleton(RoleUser.USER));
        user2.setActive(true);
        user2.setVerify(false);
        user2.setPhone_number("1234567890");
        user2.setCreatedAt(LocalDateTime.now());
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAllCustomers();

        Assertions.assertEquals(userList, result);
    }

    @Test
    @DisplayName("Test findByEmail()")
    public void testFindByEmail() {
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setEmail("test1@test.com");
        user1.setOrganization_name("testOrg1");
        user1.setRole(Collections.singleton(RoleUser.USER));
        user1.setActive(true);
        user1.setVerify(false);
        user1.setPhone_number("1234567890");
        user1.setCreatedAt(LocalDateTime.now());
        userList.add(user1);

        when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        Optional<User> result = userService.findByEmail(user1.getEmail());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user1, result.get());
    }

    @Test
    @DisplayName("Test findById()")
    public void testFindById() {
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setEmail("test1@test.com");
        user1.setOrganization_name("testOrg1");
        user1.setRole(Collections.singleton(RoleUser.USER));
        user1.setActive(true);
        user1.setVerify(false);
        user1.setPhone_number("1234567890");
        user1.setCreatedAt(LocalDateTime.now());
        userList.add(user1);

        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.of(user1));

        Optional<User> result = userService.findById(user1.getUser_id());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user1, result.get());
    }

    @Test
    @DisplayName("Test deleteCourse()")
    public void testDeleteCourse() {
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setEmail("test1@test.com");
        user1.setOrganization_name("testOrg1");
        user1.setRole(Collections.singleton(RoleUser.USER));
        user1.setActive(true);
        user1.setVerify(false);
        user1.setPhone_number("1234567890");
        user1.setCreatedAt(LocalDateTime.now());
        userList.add(user1);

        userService.deleteCourse(user1.getUser_id());

        // Verify that the deleteById() method was called with the correct argument
        verify(userRepository).deleteById(user1.getUser_id());
    }

    @Test
    @DisplayName("Test updateUser()")
    public void testUpdateUser() {
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setEmail("test1@test.com");
        user1.setOrganization_name("testOrg1");
        user1.setRole(Collections.singleton(RoleUser.USER));
        user1.setActive(true);
        user1.setVerify(false);
        user1.setPhone_number("1234567890");
        user1.setCreatedAt(LocalDateTime.now());
        userList.add(user1);

        User updatedUser = new User();
        updatedUser.setUser_id(user1.getUser_id());
        updatedUser.setOrganization_name("update Org");

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        Assertions.assertEquals(updatedUser, result);
    }
    @Test
    void testRegisterUserWithValidRequest() {
        // given
        String email = "test@example.com";
        String password = "defencing45641";
        String orgName = "Test Org";
        String phoneNumber = "1234567890";

        UserRegistrationRequest request = new UserRegistrationRequest(orgName, email, phoneNumber, password);

        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setEmail(email);
        user1.setOrganization_name(orgName);
        user1.setRole(Collections.singleton(RoleUser.USER));
        user1.setActive(true);
        user1.setVerify(false);
        user1.setPhone_number("1234567890");
        user1.setPassword(passwordEncoder.encode(password));
        user1.setCreatedAt(LocalDateTime.now());
        userList.add(user1);


        /*User user = User.builder()
                .organization_name(request.organization_name())
                .email(request.email())
                .role(Collections.singleton(RoleUser.USER))
                .active(true)
                .verify(false)
                .phone_number(request.phone_number())
                .password(passwordEncoder.encode(request.phone_number()))
                .createdAt(any(LocalDateTime.class))
                .build();*/
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when
        Map<String, Object> result = userService.registerUser(request);

        // then
        verify(userRepository).findByEmail(email);
        verify(userRepository).saveAndFlush(user1);
        assertNotNull(result);
        assertEquals("Insert success message", result.get("message"));
        UserRegistrationResponse response = (UserRegistrationResponse) result.get("results");
        assertNotNull(response);
        assertEquals(orgName, response.getOrganization_name());
        assertEquals(email, response.getEmail());
        assertEquals(Collections.singleton(RoleUser.USER), response.getRole());
        assertEquals(phoneNumber, response.getPhone_number());
    }

    @Test
    void testRegisterUserWithInvalidEmail() {
        // given
        String email = "invalid@email";
        String password = "password123";
        String orgName = "Test Org";
        String phoneNumber = "1234567890";
        UserRegistrationRequest request = new UserRegistrationRequest(orgName, email, phoneNumber, password);
        // when
        assertThrows(UserEmailNotValidException.class, () -> userService.registerUser(request));

        // then
        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    void testRegisterUserWithTakenEmail() {
        // given
        String email = "test@example.com";
        String password = "password123";
        String orgName = "Test Org";
        String phoneNumber = "1234567890";
        UserRegistrationRequest request = new UserRegistrationRequest(orgName, email, phoneNumber, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // when
        assertThrows(UserAuthenticationConflictException.class, () -> userService.registerUser(request));

        // then
        verify(userRepository).findByEmail(email);
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    void registerUser_ValidRequest_Success() {
        // given
        String email = "test@example.com";
        String organization_name = "test";
        String pass = "juh3dsg3dsb";
        String phone_number = "1234567890";
        UserRegistrationRequest request = new UserRegistrationRequest(organization_name, email, phone_number, pass);


       /* List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setEmail(email);
        user1.setOrganization_name(organization_name);
        user1.setRole(Collections.singleton(RoleUser.USER));
        user1.setActive(true);
        user1.setVerify(false);
        user1.setPhone_number("1234567890");
        user1.setPassword("encoded-pass");
        user1.setCreatedAt(LocalDateTime.parse("2023-03-12T11:14:11.217883500"));
        userList.add(user1);*/

        var user1 = User.builder()
                .organization_name(organization_name)
                .email(email)
                .role(Collections.singleton(RoleUser.USER))
                .active(true)
                .verify(false)
                .phone_number(phone_number)
                .password("encoded-pass")
                .createdAt(LocalDateTime.parse("2023-03-12T11:14:11.217883500"))
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(pass)).thenReturn("encoded-pass");
        when(userRepository.saveAndFlush(user1)).thenReturn(user1);

        // when
        Map<String, Object> result = userService.registerUser(request);

        // then
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).saveAndFlush(user1);
        verify(passwordEncoder, times(1)).encode(pass);

        //log.info(env.getActiveProfiles());
        //ReflectionTestUtils.setField(classUnderTest, "multreeContentInsert", "Data successfully stored in the database");
        assertEquals(INSERT_SUCCESS, result.get("message"));
        assertNotNull(result.get("results"));

        UserRegistrationResponse response = (UserRegistrationResponse) result.get("results");
        assertEquals(organization_name, response.getOrganization_name());
        assertEquals(email, response.getEmail());
        assertEquals(Collections.singleton(RoleUser.USER), response.getRole());
        assertEquals(phone_number, response.getPhone_number());
    }

    @Test
    void registerUser_EmailAlreadyExists_ExceptionThrown() {
        // given
        String email = "test@example.com";
        String organization_name = "test";
        String pass = "password";
        String phone_number = "1234567890";

        UserRegistrationRequest request = new UserRegistrationRequest(organization_name, email, pass, phone_number);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder().build()));

        // then
        assertThrows(UserAuthenticationConflictException.class, () -> userService.registerUser(request));
    }


}