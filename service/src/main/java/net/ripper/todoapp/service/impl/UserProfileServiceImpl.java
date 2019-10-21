package net.ripper.todoapp.service.impl;

import net.ripper.todoapp.entities.User;
import net.ripper.todoapp.entities.UserProfile;
import net.ripper.todoapp.service.UserProfileService;

import java.util.Optional;

public class UserProfileServiceImpl implements UserProfileService {

    @Override
    public Optional<UserProfile> createUserProfile(UserProfile userProfile) {
        return Optional.empty();
    }

    @Override
    public Optional<UserProfile> getUserProfileByUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<UserProfile> updateUserProfile(UserProfile userProfile) {
        return Optional.empty();
    }
}
