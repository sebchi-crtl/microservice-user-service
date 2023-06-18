package com.multree.userservice.service;

import com.multree.userservice.config.UserPrincipal;
import com.multree.userservice.config.jwt.JwtProvider;
import com.multree.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private static final String SUCCESS = "Login Successfully";

    @Autowired
    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;
    
    @Override
    public Map<String, Object> signInReturnJWT(User signInRequest)
    {


        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        var userPrincipal = (UserPrincipal) auth.getPrincipal();
        
        var jwt = jwtProvider.generateToken(userPrincipal);

        User signInUser = userPrincipal.getUser();

        signInUser.setToken(jwt);


        Map<String, Object> map = new HashMap<>();
        map.put("message", SUCCESS);
        map.put("results", signInUser);
        return map;
    }
}
