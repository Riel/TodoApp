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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

  private OwnerRepository ownerRepository;
  private TodoRepository todoRepository;
  private SettingsRepository settingsRepository;

  private final String ALL_FILTER = "Select all";
  private final String EMPTY_FILTER = "0";

  // TODO: remove this
  // can have this input when no data is set in the settings
  // make sure one cannot create a t_o_d_o that way
  private final String NULL_FILTER = "null";

  @PersistenceContext
  EntityManager entityManager;

  public String getAllFilter() {
    return ALL_FILTER;
  }

  @Autowired
  public TodoServiceImpl(OwnerRepository ownerRepository, TodoRepository todoRepository, SettingsRepository settingsRepository) {
    this.ownerRepository = ownerRepository;
    this.todoRepository = todoRepository;
    this.settingsRepository = settingsRepository;
  }

  @Override
  public List<Todo> getFilteredTodos(String ownerName, String project, String context){

    if (NULL_FILTER.equals(ownerName) || EMPTY_FILTER.equals(ownerName) || ALL_FILTER.equals(ownerName)) {
      ownerName = null;
    }

    if (NULL_FILTER.equals(project) || EMPTY_FILTER.equals(project) || ALL_FILTER.equals(project)) {
      project = null;
    }

    if (NULL_FILTER.equals(context) || EMPTY_FILTER.equals(context) || ALL_FILTER.equals(context)) {
      context = null;
    }

    String queryString = "SELECT * FROM todos";

    boolean hasFilter = false;
    if (ownerName != null){
      Optional<Owner> owner = ownerRepository.findFirstByName(ownerName);
      if (owner.isPresent()){
        queryString = queryString + " WHERE " + getFilterString("owner_id", owner.get().getId().toString());
        hasFilter = true;
      } else {
        // TODO : handle error case here
      }
    }
    if (project != null){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + getFilterString("project", project);
      hasFilter = true;
    }
    if (context != null){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + getFilterString("context", context);
      hasFilter = true;
    }
    //if (!getSettingById(1L).isShowDone()){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + "status <> " + Status.FINISHED.ordinal() ;
    //}

    queryString = queryString + " ORDER BY CASE WHEN due_date IS NULL THEN 1 ELSE 0 END, due_date, prio;";

    Query query = entityManager.createNativeQuery(queryString , Todo.class);
    List<Todo> result = query.getResultList();
    return result;
  }

  private String getFilterString(String field, String match){
    return field + " = '" + match + "'";
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

  public List<String> getOwnerNames(){
    List<String> ownerNames = new ArrayList();
    getOwners().forEach(o -> ownerNames.add(o.getName()));
    ownerNames.add(getAllFilter());
    return ownerNames;
  }

  public List<String> getProjectNames(){
    List<String> projectNames = new ArrayList<>();
    getProjects().forEach(p->projectNames.add(p));
    projectNames.add(getAllFilter());
    return projectNames;
  }

  public List<String> getContextNames(){
    List<String> contextNames = new ArrayList<>();
    getContexts().forEach(c->contextNames.add(c));
    contextNames.add(getAllFilter());
    return contextNames;
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
      return new ArrayList<String>();
    }
  }

  @Override
  public Iterable<String> getProjects() {
    // TODO: handle optional
    Optional<Setting> setting = settingsRepository.findById(1L);
    if (setting.isPresent()){
      return setting.get().getProjects();
    } else {
      return new ArrayList<String>();
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