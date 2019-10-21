package net.ripper.todoapp.service.impl;

import net.ripper.todoapp.dao.UserRepository;
import net.ripper.todoapp.entities.User;
import net.ripper.todoapp.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> createUser(String username, String password) {
        if (!userRepository.existsByUsername(username)) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setDateCreated(LocalDateTime.now());
            newUser.setDateUpdated(LocalDateTime.now());
            newUser = userRepository.save(newUser);
            return Optional.of(newUser);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
