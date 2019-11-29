package com.task.todo.services;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Owner;
import com.task.todo.models.Todo;
import com.task.todo.repositories.SettingsRepository;
import com.task.todo.repositories.OwnerRepository;
import com.task.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

  private OwnerRepository ownerRepository;
  private TodoRepository todoRepository;
  private SettingsRepository contextRepository;

  @Autowired
  public TodoServiceImpl(OwnerRepository ownerRepository, TodoRepository todoRepository, SettingsRepository contextRepository) {
    this.ownerRepository = ownerRepository;
    this.todoRepository = todoRepository;
    this.contextRepository = contextRepository;
  }

  @Override
  public Iterable<Todo> getAllTodo() {
    return todoRepository.findAll();
  }

  @Override
  public Todo getTodo(Long id) {
    Optional<Todo> todo = todoRepository.findById(id);
    return todo.isPresent() ? todo.get() : null ;
  }

  @Override
  public void saveTodo(Todo todo){
    todoRepository.save(todo);
  }

  @Override
  public Iterable<Owner> getOwners() {
    return ownerRepository.findAll();
  }

  public List<String> getPriorities (){
    return Arrays.stream(Priority.values()).map(p -> p.toString()).collect(Collectors.toList());
  }

  public List<String> getStatuses (){
    return Arrays.stream(Status.values()).map(p -> p.toString()).collect(Collectors.toList());
  }

  @Override
  public Iterable<String> getContexts() {
    // TODO: handle optional
    return contextRepository.findById(1L).get().getContexts();
  }

  @Override
  public Iterable<String> getProjects() {
    // TODO: handle optional
    return contextRepository.findById(1L).get().getProjects();
  }
}