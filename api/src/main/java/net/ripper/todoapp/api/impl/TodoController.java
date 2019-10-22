package net.ripper.todoapp.api.impl;

import io.swagger.annotations.Api;
import net.ripper.todoapp.api.TodoV1Api;
import net.ripper.todoapp.domain.TodoEntry;
import net.ripper.todoapp.entities.Todo;
import net.ripper.todoapp.entities.User;
import net.ripper.todoapp.service.TodoService;
import net.ripper.todoapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@Api(tags = "TodoV1")
public class TodoController implements TodoV1Api {
    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<TodoEntry> createTodo(@Valid TodoEntry todoEntry) {
        Optional<Todo> todo = todoService.createTodo(Todo.builder()
                .description(todoEntry.getDescription())
                .title(todoEntry.getTitle())
                .ownerUser(userService.getUserById(todoEntry.getUser().getId()).get())
                .build());

        if (todo.isPresent()) {
            TodoEntry entry = toEntry(todo.get());
            return ResponseEntity.ok(entry);
        }
        return ResponseEntity.badRequest().build();
    }

    private TodoEntry toEntry(Todo t) {
        return new TodoEntry()
                .dateUpdated(t.getDateUpdated().toString())
                .dateCreated(t.getDateCreated().toString())
                .description(t.getDescription())
                .title(t.getTitle())
                .doneStatus(t.isDoneStatus())
                .user(toUserEntry(t.getOwnerUser()))
                .id(t.getId());
    }

    private net.ripper.todoapp.domain.User toUserEntry(User u) {
        return new net.ripper.todoapp.domain.User()
                .id(u.getId())
                .username(u.getUsername());
    }

    @Override
    public ResponseEntity<TodoEntry> getTodoById(@PathVariable("id") Integer id) {
        Optional<Todo> todoById = todoService.getTodoById(id);
        if (todoById.isPresent()) {
            TodoEntry t = toEntry(todoById.get());
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<TodoEntry> updateTodoById(
            @PathVariable("id") Integer id, @Valid TodoEntry todoEntry) {
        User loggedInUser = userService.getUserById(todoEntry.getUser().getId()).get();
        Todo todo = Todo.builder()
                .ownerUser(loggedInUser)
                .title(todoEntry.getTitle())
                .description(todoEntry.getDescription())
                .doneStatus(todoEntry.getDoneStatus())
                .id(id)
                .dateUpdated(LocalDateTime.now())
                .build();
        Optional<Todo> todo1 = todoService.updateTodoById(todo);
        if (todo1.isPresent())
            return ResponseEntity.ok(toEntry(todo1.get()));
        return ResponseEntity.badRequest().build();
    }
}
