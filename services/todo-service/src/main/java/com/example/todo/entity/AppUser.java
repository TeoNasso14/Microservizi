package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
  
@Entity
@Table(name = "app_user")
@Data
public class AppUser {
  @Id @GeneratedValue private Long id;
  @Column(unique = true) private String username;
  private String password;
  // Lombok @Data generates all getters, setters, toString, equals, and hashCode
}
