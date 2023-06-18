package com.multree.userservice.entity;

public record UserLoginRequest(

        String username,
        String password) {
}
