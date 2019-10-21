package net.ripper.todoapp.service;

import net.ripper.todoapp.entities.Todo;
import net.ripper.todoapp.entities.User;

import java.util.Optional;

public interface TodoService {
    Optional<Todo> createTodo(Todo entry);

    Optional<Todo> getTodoById(int id);

    Optional<Todo> updateTodoById(Todo updatedTodo);

    Iterable<Todo> findByUser(User user);
}
