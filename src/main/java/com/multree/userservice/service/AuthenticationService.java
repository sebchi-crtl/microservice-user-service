package com.multree.userservice.service;

import com.multree.userservice.entity.User;

import java.util.Map;

public interface AuthenticationService {
    Map<String, Object> signInReturnJWT(User userSignInRequest);
}
