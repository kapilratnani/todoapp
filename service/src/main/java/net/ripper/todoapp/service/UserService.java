package net.ripper.todoapp.service;

import net.ripper.todoapp.entities.User;

import java.util.Optional;

public interface UserService {
    /**
     * @param username
     * @param password
     * @return a user if created, empty if already exists
     */
    Optional<User> createUser(String username, String password);

    Optional<User> getUserById(int id);

    Optional<User> getUserByUserName(String username);
}
