package com.multree.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multree.userservice.entity.UserRegistrationRequest;
import com.multree.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private UserRepository productRepository;

	@Test
	void shouldCreateProduct() throws Exception {
		UserRegistrationRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private UserRegistrationRequest getProductRequest() {
		UserRegistrationRequest request = new UserRegistrationRequest("orgName", "email@mail.com", "09122411118", "ghsvhsjshks");

		return request;
	}
	@Test
	public void contextLoads() {
		assertTrue(true);
	}

}
