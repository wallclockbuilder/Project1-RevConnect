package com.revature.Project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Project1.Controllers.UserController;
import com.revature.Project1.Models.User;
import com.revature.Project1.Repositories.UserRepository;
import com.revature.Project1.Security.JwtRequestFilter;
import com.revature.Project1.Security.JwtUtil;
import com.revature.Project1.Services.CustomUserDetailsService;
import com.revature.Project1.Services.EmailService;
import com.revature.Project1.Services.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;


@WebMvcTest({UserController.class})
@ActiveProfiles("test")  // Use the application-test.properties configuration
@Import(TestSecurityConfig.class)
class Project1ApplicationTests {
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

	@MockBean
	private EmailService emailService;

    private User guestUser;
	
	@MockBean
	private CustomUserDetailsService customUserDetailsService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		guestUser = new User();
		guestUser.setUsername("guest");
		guestUser.setEmail("guest@internet.com");
		guestUser.setPassword("password");
	}
	// 	As a user, I should be able to:
	//  - Register myself and create an account
	@Test
	void userCanRegisterAndCreateAccount() throws JsonProcessingException, Exception {
		// Given
			when(userService.createUser(any(User.class))).thenReturn(guestUser);
		// When
		// user registers and creates an account
		mockMvc.perform(post("/api/users")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(guestUser)))
		// Then
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.username").value(guestUser.getUsername()))
		.andExpect(jsonPath("$.email").value(guestUser.getEmail()));
	}

	// 	As a user, I should be able to:
	// - Log in to my account
	@Test
	void userCanLogInToAccount() throws JsonProcessingException, Exception {
		// Given
		// Mock user service and JWT util
        when(userService.getUserByUsername(any(String.class))).thenReturn(guestUser);
        when(userService.checkPassword(any(String.class), any(String.class))).thenReturn(true);
		// When
		// user logs in to account
			mockMvc.perform(post("/api/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(guestUser)))
		// Then
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.token").value("fake-jwt-token"))
		.andExpect(cookie().value("Authentication", "fake-jwt-token"))
		// user is able to log in to account
		.andExpect(jsonPath("$.user.username").value(guestUser.getUsername()))
		.andExpect(jsonPath("$.user.email").value(guestUser.getEmail()));
	}

}
