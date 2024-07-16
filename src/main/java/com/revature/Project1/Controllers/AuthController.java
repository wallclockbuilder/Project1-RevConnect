package com.revature.Project1.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.Project1.Models.AuthRequest;
import com.revature.Project1.Models.AuthResponse;
import com.revature.Project1.Security.JwtUtil;
import com.revature.Project1.Services.CustomUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authenticationRequest, HttpServletResponse response) throws Exception {
        try {
            logger.info("Authenticating user: {}", authenticationRequest.getUsername());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            Cookie cookie = new Cookie("token", jwt);
//            cookie.setHttpOnly(true);
//            cookie.setPath("/");
            response.addCookie(cookie);

            logger.debug("User authenticated successfully: {}", authenticationRequest.getUsername());

            return new AuthResponse(jwt);
        } catch (Exception e) {
            logger.error("Authentication error: {}", e.getMessage(), e);
            throw e;
        }
    }
}