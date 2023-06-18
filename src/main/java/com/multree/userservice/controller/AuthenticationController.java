package com.multree.userservice.controller;

import com.multree.userservice.entity.User;
import com.multree.userservice.entity.UserRegistrationRequest;
import com.multree.userservice.service.AuthenticationService;
import com.multree.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("sign-up")
    public ResponseEntity<?> registerCustomer(@RequestBody UserRegistrationRequest userRequest){
        log.info("new user registration {}", userRequest);


        return new ResponseEntity<>(userService.registerUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user){
        log.info("login user {}", user);
//        User user = customerService.loginCustomer(customerRequest);
//        ResponseEntity ok = ResponseEntity.ok(customer);
//
//        return user;
        return new ResponseEntity<>(authenticationService.signInReturnJWT(user), HttpStatus.OK);
        //return new ResponseEntity<>(userService.loginCustomer(user), HttpStatus.OK);

    }
}
