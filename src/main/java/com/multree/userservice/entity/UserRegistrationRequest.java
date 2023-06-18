package com.multree.userservice.entity;


public record UserRegistrationRequest(
        String organization_name,

        String email,

        String phone_number,

        String password) {


    public static UserRegistrationRequest of(String multree, String s, String s1, String password123) {
        return null;
    }

}
