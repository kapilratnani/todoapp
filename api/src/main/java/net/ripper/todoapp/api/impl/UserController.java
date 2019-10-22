package net.ripper.todoapp.api.impl;


import io.swagger.annotations.Api;
import net.ripper.todoapp.api.UserV1Api;
import net.ripper.todoapp.domain.AccessTokenGrant;
import net.ripper.todoapp.domain.UserPasswordRequest;
import net.ripper.todoapp.domain.UserProfile;
import net.ripper.todoapp.entities.User;
import net.ripper.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@Api(tags = "UserV1")
public class UserController implements UserV1Api {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<UserProfile> getLoggedInUser() {

        return null;
    }

    @Override
    public ResponseEntity<AccessTokenGrant> loginUser(@Valid UserPasswordRequest userPasswordRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> registerUser(@Valid UserPasswordRequest userPasswordRequest) {
        Optional<User> user = userService.createUser(userPasswordRequest.getUsername(),
                userPasswordRequest.getPassword());
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
