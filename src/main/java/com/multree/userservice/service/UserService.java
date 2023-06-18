package com.multree.userservice.service;

import com.multree.userservice.entity.User;
import com.multree.userservice.entity.UserRegistrationRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Map<String, Object> registerUser(UserRegistrationRequest request);

    List<User> findAllCustomers();

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID user_id);

    void deleteCourse(UUID customerId);

}
