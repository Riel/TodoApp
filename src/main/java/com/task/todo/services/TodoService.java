package com.task.todo.services;

import com.task.todo.models.Owner;
import com.task.todo.models.Setting;
import com.task.todo.models.Todo;

import java.util.List;

public interface TodoService {
  Iterable<Todo> getAllTodo();
  Todo getTodo(Long id);
  void saveTodo(Todo todo);
  Iterable<Owner> getOwners();
  List<String> getPriorities();
  List<String> getStatuses();
  Iterable<String> getContexts();
  Iterable<String> getProjects();
  Setting getSettingById(Long id);
  void deleteById(Long id);
  void deleteOwner(String name);
  Owner saveOwner(Owner newOwner);
}
