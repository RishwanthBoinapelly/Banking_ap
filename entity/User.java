package com.example.demo.entity;

import jakarta.persistence.Entity;

import com.example.demo.dto.UserDTO;

// import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name should not be empty")

    @Column(nullable = false)
    private String name;
    @Email(message = "must be a well-formed email address")
    @NotEmpty(message = "Email should not be empty")
    @Column(unique = true, nullable = false)
    private String email;
    @NotEmpty(message = "Password should not be empty")
    @Column(nullable = false,unique=true)
    private String password;

    // Getters, Setters, and Constructors
    public User() {}

    // Constructor to initialize User from UserDTO
    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
}

