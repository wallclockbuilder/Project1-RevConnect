package com.revature.Project1.Controllers;

import com.revature.Project1.Models.User;
import com.revature.Project1.Security.LoginResponseDto;
import com.revature.Project1.Services.UserService;

import com.revature.Project1.Models.AuthRequest;

import com.revature.Project1.Security.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public LoginResponseDto createAuthenticationToken(@RequestBody AuthRequest authenticationRequest, HttpServletResponse response) throws Exception {
        User user = userService.getUserByUsername(authenticationRequest.getUsername());

        if (user != null && userService.checkPassword(authenticationRequest.getPassword(), user.getPassword())) {
            final String jwt = jwtUtil.generateToken(user.getUsername());

            Cookie cookie = new Cookie("token", jwt);

            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return new LoginResponseDto(jwt, user);
        } else {
            throw new Exception("Invalid credentials");
        }
    }
}