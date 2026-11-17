package com.example.demo;

import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/admin")
public class UserController {

    private final JwtUtil jwtUtil;

    public UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

 
    @GetMapping("/generate")
    public String generateToken() {
        return jwtUtil.generateToken("Ravi", "ADMIN");
    }

    @PutMapping("/validate")
    public String validateToken(
            HttpServletRequest request) {
    	String authHeader = request.getHeader("Authorization");
    	String token = authHeader.substring(7);
        Claims claim = jwtUtil.validateToken(token);

        String role = claim.get("role", String.class);

        if (!"ADMIN".equals(role)) {
            return "Access Denied: Admin Only";
        }

        return "User Updated Successfully";
    }
}