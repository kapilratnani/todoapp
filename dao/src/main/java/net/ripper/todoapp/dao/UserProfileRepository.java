package net.ripper.todoapp.dao;

import net.ripper.todoapp.entities.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {
}
