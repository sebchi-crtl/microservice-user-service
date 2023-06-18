package com.multree.userservice;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean(name = "user_id_sequence")
	public UUIDGenerator uuidGenerator() {
		return new UUIDGenerator();
	}
}
