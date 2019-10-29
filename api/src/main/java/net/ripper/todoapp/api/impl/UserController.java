package net.ripper.todoapp.api.impl;


import io.swagger.annotations.Api;
import net.ripper.todoapp.api.UserV1Api;
import net.ripper.todoapp.api.security.jwt.JwtTokenProvider;
import net.ripper.todoapp.api.security.UserSecurityService;
import net.ripper.todoapp.domain.AccessTokenGrant;
import net.ripper.todoapp.domain.User;
import net.ripper.todoapp.domain.UserPasswordRequest;
import net.ripper.todoapp.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@Api(tags = "UserV1")
public class UserController implements UserV1Api {
    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<UserProfile> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            throw new BadCredentialsException("No Token provided");

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new UserProfile().name(principal.getUsername()));
    }

    @Override
    public ResponseEntity<AccessTokenGrant> loginUser(@Valid UserPasswordRequest userPasswordRequest) {
        return ResponseEntity.ok(userSecurityService.login(userPasswordRequest)
                .map(user -> new AccessTokenGrant()
                        .accessToken(jwtTokenProvider.createToken(user.getUsername())))
                .orElseThrow(() -> new BadCredentialsException("Username or password is wrong")));
    }

    @Override
    public ResponseEntity<Void> registerUser(@Valid UserPasswordRequest userPasswordRequest) {
        Optional<User> user = userSecurityService.createUser(userPasswordRequest);
        if (user.isPresent()) {
            try {
                return ResponseEntity.created(new URI("/user/" + user.get().getId())).build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
