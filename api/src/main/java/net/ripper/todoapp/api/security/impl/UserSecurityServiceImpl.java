package net.ripper.todoapp.api.security.impl;

import net.ripper.todoapp.api.security.UserSecurityService;
import net.ripper.todoapp.domain.User;
import net.ripper.todoapp.domain.UserPasswordRequest;
import net.ripper.todoapp.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserSecurityServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> createUser(UserPasswordRequest userPasswordRequest) {
        Optional<net.ripper.todoapp.entities.User> user = userService.createUser(userPasswordRequest.getUsername(),
                passwordEncoder.encode(userPasswordRequest.getPassword()));
        return user.map(user1 -> new User()
                .username(user1.getUsername())
                .id(user1.getId())
                .dateCreated(user1.getDateCreated().toString())
        );
    }

    @Override
    public Optional<User> login(UserPasswordRequest userPasswordRequest) {
        Optional<net.ripper.todoapp.entities.User> user =
                userService.getUserByUserName(userPasswordRequest.getUsername());
        if (user.isPresent() &&
                passwordEncoder.matches(userPasswordRequest.getPassword(),
                        user.get().getPassword())) {
            net.ripper.todoapp.entities.User _user = user.get();
            return Optional.of(
                    new User().id(_user.getId())
                            .username(_user.getUsername())
                            .dateCreated(_user.getDateCreated().toString())
            );
        }

        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByUserName(username).map(
                user -> new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return Collections.emptyList();
                    }

                    @Override
                    public String getPassword() {
                        return user.getPassword();
                    }

                    @Override
                    public String getUsername() {
                        return user.getUsername();
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                }
        ).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
