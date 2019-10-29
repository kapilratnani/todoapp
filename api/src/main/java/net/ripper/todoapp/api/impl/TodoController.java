package net.ripper.todoapp.api.impl;

import io.swagger.annotations.Api;
import net.ripper.todoapp.api.TodoV1Api;
import net.ripper.todoapp.domain.CreateTodoRequest;
import net.ripper.todoapp.domain.TodoEntry;
import net.ripper.todoapp.domain.UpdateTodoRequest;
import net.ripper.todoapp.entities.Todo;
import net.ripper.todoapp.entities.User;
import net.ripper.todoapp.service.TodoService;
import net.ripper.todoapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TodoEntry> createTodo(@Valid CreateTodoRequest createTodoRequest) {
        Optional<User> loggedInUser = getLoggedInUser();

        if (!loggedInUser.isPresent())
            return ResponseEntity.badRequest().build();

        Optional<Todo> todo = todoService.createTodo(Todo.builder()
                .description(createTodoRequest.getDescription().isPresent() ?
                        createTodoRequest.getDescription().get() : "")
                .title(createTodoRequest.getTitle())
                .ownerUser(loggedInUser.get())
                .build());

        if (todo.isPresent()) {
            TodoEntry entry = toEntry(todo.get());
            return ResponseEntity.ok(entry);
        }
        return ResponseEntity.badRequest().build();
    }

    private Optional<User> getLoggedInUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserByUserName(principal.getUsername());
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
        Optional<User> loggedInUser = getLoggedInUser();

        if (!loggedInUser.isPresent())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Optional<Todo> todoById = todoService.getTodoById(id);
        if (todoById.isPresent() && todoById.get()
                .getOwnerUser().getUsername()
                .equals(loggedInUser.get().getUsername())) {
            TodoEntry t = toEntry(todoById.get());
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<TodoEntry> updateTodoById(
            @PathVariable("id") Integer id, @Valid UpdateTodoRequest updateTodoRequest) {

        Optional<User> loggedInUser = getLoggedInUser();

        if (!loggedInUser.isPresent())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Optional<Todo> todoById = todoService.getTodoById(id);

        if (!todoById.isPresent())
            return ResponseEntity.badRequest().build();

        if (!todoById.get().getOwnerUser().getUsername().equals(loggedInUser.get().getUsername()))
            return ResponseEntity.badRequest().build();

        Todo todo = Todo.builder()
                .ownerUser(loggedInUser.get())
                .title(
                        updateTodoRequest.getTitle().isPresent() ? updateTodoRequest.getTitle().get() :
                                todoById.get().getTitle()
                )
                .description(updateTodoRequest.getDescription().isPresent() ?
                        updateTodoRequest.getDescription().get() :
                        todoById.get().getDescription()
                )
                .doneStatus(updateTodoRequest.getDoneStatus().isPresent() ?
                        updateTodoRequest.getDoneStatus().get() :
                        todoById.get().isDoneStatus())
                .id(id)
                .dateCreated(todoById.get().getDateCreated())
                .dateUpdated(LocalDateTime.now())
                .build();

        Optional<Todo> todo1 = todoService.updateTodoById(todo);
        if (todo1.isPresent())
            return ResponseEntity.ok(toEntry(todo1.get()));
        return ResponseEntity.badRequest().build();
    }
}
