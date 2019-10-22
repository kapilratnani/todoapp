package net.ripper.todoapp.service.impl;

import net.ripper.todoapp.dao.TodoRepository;
import net.ripper.todoapp.entities.Todo;
import net.ripper.todoapp.entities.User;
import net.ripper.todoapp.service.TodoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Optional<Todo> createTodo(Todo entry) {
        entry.setDateCreated(LocalDateTime.now());
        entry.setDateUpdated(LocalDateTime.now());
        return Optional.of(todoRepository.save(entry));
    }

    @Override
    public Optional<Todo> getTodoById(int id) {
        return todoRepository.findById(id);
    }

    @Override
    public Optional<Todo> updateTodoById(Todo updatedTodo) {
        return Optional.of(todoRepository.save(updatedTodo));
    }

    @Override
    public Iterable<Todo> findByUser(User user) {
        return todoRepository.findAllByOwnerUser(user);
    }
}
