package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.DtoConverter;
import com.revature.Project1.DTO.UserDto;
import com.revature.Project1.Models.User;
import com.revature.Project1.Security.LoginResponseDto;
import com.revature.Project1.Services.UserService;

import com.revature.Project1.Models.AuthRequest;

import com.revature.Project1.Security.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    private final JwtUtil jwtUtil;


    private final UserService userService;
    @Autowired
    public AuthController(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<LoginResponseDto> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest, HttpServletResponse response) throws Exception {
        User user = userService.getUserByUsername(authenticationRequest.getUsername());

        if (user != null && userService.checkPassword(authenticationRequest.getPassword(), user.getPassword())) {
            final String jwt = jwtUtil.generateToken(user.getUsername());
            System.out.println("Generated JWT: " + jwt);
            Cookie cookie = new Cookie("Authentication", jwt);

            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            UserDto userDto = DtoConverter.toUserDto(user);
            return ResponseEntity.ok(new LoginResponseDto(jwt, userDto));
        } else {
            throw new Exception("Invalid credentials");
        }
    }
}