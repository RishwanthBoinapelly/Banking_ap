package com.example.demo.Service;

import com.example.demo.dto.UserDTO;

public interface UserService {

    // Get user details by ID
    UserDTO getUserById(Long id);

    // Create a new user
    UserDTO createUser(UserDTO userDTO);

    // Update a user
    UserDTO updateUser(Long id, UserDTO userDTO);

    // Delete a user
    void deleteUser(Long id);
}
