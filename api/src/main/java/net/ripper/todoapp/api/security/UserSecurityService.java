package net.ripper.todoapp.api.security;

import net.ripper.todoapp.domain.User;
import net.ripper.todoapp.domain.UserPasswordRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserSecurityService extends UserDetailsService {
    Optional<User> createUser(UserPasswordRequest userPasswordRequest);
    Optional<User> login(UserPasswordRequest userPasswordRequest);
}
