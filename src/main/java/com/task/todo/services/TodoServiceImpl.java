package com.task.todo.services;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Owner;
import com.task.todo.models.Setting;
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
  private SettingsRepository settingsRepository;

  @Autowired
  public TodoServiceImpl(OwnerRepository ownerRepository, TodoRepository todoRepository, SettingsRepository settingsRepository) {
    this.ownerRepository = ownerRepository;
    this.todoRepository = todoRepository;
    this.settingsRepository = settingsRepository;
  }

  @Override
  public List<Todo> getAllTodo() {
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
    Optional<Setting> setting = settingsRepository.findById(1L);
    if (setting.isPresent()){
      return setting.get().getContexts();
    } else {
      return null;
    }
  }

  @Override
  public Iterable<String> getProjects() {
    // TODO: handle optional
    Optional<Setting> setting = settingsRepository.findById(1L);
    if (setting.isPresent()){
      return setting.get().getProjects();
    } else {
      return null;
    }
  }

  @Override
  public Setting getSettingById(Long id) {
    // TODO: handle optional
    return settingsRepository.findById(1L).orElse(null);
  }

  @Override
  public void saveSetting(Setting setting) {
    settingsRepository.save(setting);
  }

  @Override
  public void deleteById(Long id) {
    todoRepository.deleteById(id);
  }

  @Override
  public void deleteOwner(String name){
    ownerRepository.deleteOwnerByName(name);
  }

  @Override
  public void deleteProject(String projectName) {
    //TODO: handle exceptions here
    Optional<Setting> setting = settingsRepository.findById(1L);
    if (setting.isPresent()) {
      Setting itemToSave = setting.get();
      itemToSave.getProjects().remove(projectName);
      saveSetting(itemToSave);
    }
  }

  @Override
  public void deleteContext(String contextName) {
    //TODO: handle exceptions here
    Optional<Setting> setting = settingsRepository.findById(1L);
    if (setting.isPresent()) {
      Setting itemToSave = setting.get();
      itemToSave.getContexts().remove(contextName);
      saveSetting(itemToSave);
    }
  }

  @Override
  public Owner saveOwner(Owner newOwner){
    return ownerRepository.save(newOwner);
  }
}