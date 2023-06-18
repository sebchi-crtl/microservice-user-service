package com.multree.userservice.config;

import com.multree.userservice.entity.User;
import com.multree.userservice.service.UserService;
import com.multree.userservice.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class CustomUserDetailsService implements  UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("check for user email " + email);

        User user = userService
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().toString()));

        return UserPrincipal.builder()
                .user(user)
                .id(user.getUser_id())
                .email(email)
                .password(user.getPassword())
                .authorities(authorities)
                .build();


//      We can use  this import org.springframework.security.core.userdetails.User (this is made from spring boot) OR  the userPrincipal (this is custom);
//        authorities OR USER.name() OR USER.getGrantedAuthorities() OR (GrantedAuthority) user.getRoleName()
    }

}
