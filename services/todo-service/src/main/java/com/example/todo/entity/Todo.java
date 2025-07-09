package com.example.todo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "task")
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Lombok @Data generates all getters, setters, toString, equals, and hashCode
}