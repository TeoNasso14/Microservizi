package com.example.todo.controller;

import com.example.todo.service.UserDetailService;
import com.example.todo.dto.AuthResponse;
import com.example.todo.entity.AppUser;
import com.example.todo.security.JwtUtil;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager am;
    @Autowired
    UserDetailService uds;
    @Autowired
    JwtUtil jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AppUser r) {
        am.authenticate(new UsernamePasswordAuthenticationToken(r.getUsername(), r.getPassword()));
        UserDetails u = uds.loadUserByUsername(r.getUsername());
        return new AuthResponse(jwt.generateToken(u));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser r) {

        if (uds.userExists(r.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username già esistente");
        }
        // crea e salva l’utente con password encoded
        AppUser user = new AppUser();
        user.setUsername(r.getUsername());
        user.setPassword(passwordEncoder.encode(r.getPassword()));
        uds.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(jwt.generateToken(new org.springframework.security.core.userdetails.User(r.getUsername(), r.getPassword(), java.util.Collections.emptyList()))));
    }

}
