package net.ripper.todoapp.dao;

import net.ripper.todoapp.entities.Todo;
import net.ripper.todoapp.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class LibraryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertNotNull(dataSource);
        assertNotNull(jdbcTemplate);
        assertNotNull(entityManager);
        assertNotNull(userRepository);
        assertNotNull(todoRepository);
    }

    @Test
    public void testUserExists() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        userRepository.save(user);
        assertTrue(userRepository.existsByUsername("test"));
    }

    @Test
    public void testGetTodo() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        userRepository.save(user);

        Iterable<Todo> allByUser = todoRepository.findAllByOwnerUser(user);
        List<Todo> collect = StreamSupport.stream(allByUser.spliterator(),
                false).collect(Collectors.toList());
        assertEquals(0, collect.size());

        Todo todo1 = Todo.builder()
                .dateCreated(LocalDateTime.now())
                .dateUpdated(LocalDateTime.now())
                .ownerUser(user)
                .title("a")
                .description("b")
                .build();

        Todo todo2 = Todo.builder()
                .dateCreated(LocalDateTime.now())
                .dateUpdated(LocalDateTime.now())
                .ownerUser(user)
                .title("c")
                .description("d")
                .build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);

        allByUser = todoRepository.findAllByOwnerUser(user);
        collect = StreamSupport.stream(allByUser.spliterator(),
                false).collect(Collectors.toList());
        assertEquals(2, collect.size());
    }
}
