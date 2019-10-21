package net.ripper.todoapp.service.impl;

import net.ripper.todoapp.entities.User;
import net.ripper.todoapp.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
@Import({UserServiceImpl.class})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        User u = userService.createUser("test", "test").orElse(new User());
        Assert.assertEquals("test", u.getUsername());
    }

    @Test
    public void getUserById() {
        Optional<User> userById = userService.getUserById(5);
        Assert.assertFalse(userById.isPresent());
        Optional<User> user = userService.createUser("test2", "test2");
        if (user.isPresent()) {
            Optional<User> userById1 = userService.getUserById(user.get().getId());
            Assert.assertTrue(userById1.isPresent());
        } else {
            Assert.fail("user should have been created!");
        }
    }

    @Test
    public void getUserByUserName() {
        Optional<User> userByUserName = userService.getUserByUserName("test3");
        Assert.assertFalse(userByUserName.isPresent());
        Optional<User> user = userService.createUser("test3", "test3");
        if (user.isPresent()) {
            Optional<User> userByUserName1 = userService.getUserByUserName(user.get().getUsername());
            Assert.assertTrue(userByUserName1.isPresent());
        } else {
            Assert.fail("user should have been created!");
        }
    }
}