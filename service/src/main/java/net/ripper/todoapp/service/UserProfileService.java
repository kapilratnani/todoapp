package net.ripper.todoapp.service;

import net.ripper.todoapp.entities.User;
import net.ripper.todoapp.entities.UserProfile;

import java.util.Optional;

public interface UserProfileService {
    Optional<UserProfile> createUserProfile(UserProfile userProfile);

    Optional<UserProfile> getUserProfileByUser(User user);

    Optional<UserProfile> updateUserProfile(UserProfile userProfile);
}
