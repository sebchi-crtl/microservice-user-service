package com.multree.userservice.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/demo")
public class UserControllerDemo {



    @GetMapping
    public ResponseEntity<String> findOne() {
        return ResponseEntity.ok("welcome");

    }
}
