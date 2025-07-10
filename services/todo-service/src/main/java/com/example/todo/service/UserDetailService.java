package com.example.todo.service;

import com.example.todo.entity.AppUser;
import com.example.todo.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired AppUserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String u) {
        AppUser user = repo.findByUsername(u)
            .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return User.withUsername(user.getUsername())
            .password(user.getPassword())
            .roles("USER").build();
    }

    public void saveUser(AppUser user) {
        repo.save(user);
    }
    public boolean userExists(String username) {
        return repo.findByUsername(username).isPresent();
    }
}