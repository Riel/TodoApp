package com.task.todo.controllers;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Owner;
import com.task.todo.models.Setting;
import com.task.todo.models.Todo;
import com.task.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TodoController {

  private TodoService todoService;
  private final String EMPTY_FILTER = "Select all";

  @Autowired
  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String getMainPage(Model model,
                            @RequestParam (required = false) String owner,
                            @RequestParam (required = false) String project,
                            @RequestParam (required = false) String context){

    if (owner != null && owner.equals(EMPTY_FILTER)) {
      owner = null;
    }

    if (project != null && project.equals(EMPTY_FILTER)) {
      project = null;
    }

    if (context != null && context.equals(EMPTY_FILTER)) {
      context = null;
    }

    String finalOwner = owner;
    String finalProject = project;
    String finalContext = context;

    boolean hasOwner = owner != null;
    boolean hasProject = project != null;
    boolean hasContext = context != null;

    List<Todo> allTodos = todoService.getAllTodo();
    List<Todo> displayedTodos = new ArrayList<>();

    if (hasOwner && hasProject && hasContext){
      displayedTodos = allTodos.stream()
              .filter(t -> t.getOwner().getName().equals(finalOwner))
              .filter(t -> t.getProject().equals(finalProject))
              .filter(t -> t.getContext().equals(finalContext))
              .collect(Collectors.toList());
    } else if (hasOwner && hasProject && !hasContext) {
      displayedTodos = allTodos.stream()
              .filter(t -> t.getOwner().getName().equals(finalOwner))
              .filter(t -> t.getProject().equals(finalProject))
              .collect(Collectors.toList());
    } else if (hasOwner && !hasProject && hasContext) {
      displayedTodos = allTodos.stream()
              .filter(t -> t.getOwner().getName().equals(finalOwner))
              .filter(t -> t.getContext().equals(finalContext))
              .collect(Collectors.toList());
    } else if (hasOwner && !hasProject && !hasContext) {
      displayedTodos = allTodos.stream()
              .filter(t -> t.getOwner().getName().equals(finalOwner))
              .collect(Collectors.toList());
    } else if (!hasOwner && hasProject && hasContext) {
      displayedTodos = allTodos.stream()
              .filter(t -> t.getProject().equals(finalProject))
              .filter(t -> t.getContext().equals(finalContext))
              .collect(Collectors.toList());
    } else if (!hasOwner && hasProject && !hasContext) {
      displayedTodos = allTodos.stream()
              .filter(t -> t.getProject().equals(finalProject))
              .collect(Collectors.toList());
    } else if (!hasOwner && !hasProject && hasContext) {
      displayedTodos = allTodos.stream()
              .filter(t -> t.getContext().equals(finalContext))
              .collect(Collectors.toList());
    }  if (!hasOwner && !hasProject && !hasContext) {
      displayedTodos = allTodos;
    }

    displayedTodos = displayedTodos.stream().sorted().collect(Collectors.toList());

    model.addAttribute("todos", displayedTodos);

    List<String> ownerNames = new ArrayList();
    todoService.getOwners().forEach(o -> ownerNames.add(o.getName()));
    ownerNames.add(EMPTY_FILTER);
    model.addAttribute("owners", ownerNames);
    List<String> projectNames = new ArrayList<>();
    todoService.getProjects().forEach(p->projectNames.add(p));
    projectNames.add(EMPTY_FILTER);
    model.addAttribute("projects", projectNames);
    List<String> contextNames = new ArrayList<>();
    todoService.getContexts().forEach(c->contextNames.add(c));
    contextNames.add(EMPTY_FILTER);
    model.addAttribute("contexts", contextNames);

    model.addAttribute("selectedOwner", owner);
    model.addAttribute("selectedProject", project);
    model.addAttribute("selectedContext", context);

    return "main";
  }

  @RequestMapping(path = "/todo/{id}/edit", method = RequestMethod.GET)
  public String showEditForm(Model model, @PathVariable Long id) {
    Todo todo = todoService.getTodo(id);
    addTodoAttributes(model, todo);
    return "edit_todo";
  }

  @RequestMapping(path = "/todo/{id}/edit", method = RequestMethod.POST)
  public String updateTodo(@PathVariable Long id, @ModelAttribute Todo todo) {
    todo.setId(id);
    todoService.saveTodo(todo);
    return "redirect:/";
  }

  @RequestMapping(path = "/todo/add", method = RequestMethod.GET)
  public String showAddForm(Model model) {
    Todo todo = createEmptyTodo();
    addTodoAttributes(model, todo);
    return "add_todo";
  }

  // TODO: move to service
  private Todo createEmptyTodo() {
    Todo todo = new Todo("", "", "", "", LocalDate.now(), Priority.LOW, Status.NOT_STARTED, new Owner("", ""));
    todo.setId(0L);
    return todo;
  }

  @RequestMapping(path = "/todo/add", method = RequestMethod.POST)
  public String addTodo(@ModelAttribute Todo todo) {
    todoService.saveTodo(todo);
    return "redirect:/";
  }

  @RequestMapping(path = "/todo/{id}/delete", method = RequestMethod.GET)
  public String deleteTodo(@PathVariable Long id) {
    todoService.deleteById(id);
    return "redirect:/";
  }

  @RequestMapping(path = "/todo/{id}/done", method = RequestMethod.POST)
  public String completeTodo(@PathVariable Long id) {
    Todo todo = todoService.getTodo(id);
    todo.setStatus(Status.FINISHED);
    todoService.saveTodo(todo);
    return "redirect:/";
  }

  @RequestMapping(path = "/settings", method = RequestMethod.GET)
  public String showSettings(Model model) {
    // TODO: make it a query:
    List<String> ownerNames = new ArrayList();
    todoService.getOwners().forEach(o -> ownerNames.add(o.getName()));
    model.addAttribute("owners", ownerNames);
    model.addAttribute("projects", todoService.getProjects());
    model.addAttribute("contexts", todoService.getContexts());

    return "settings";
  }

  private void addTodoAttributes(Model model, Todo todo) {
    model.addAttribute("todo", todo);
    model.addAttribute("owners", todoService.getOwners());
    model.addAttribute("priorities", Priority.values());
    model.addAttribute("statuses", Status.values());
    model.addAttribute("contexts", todoService.getContexts());
    model.addAttribute("projects", todoService.getProjects());
  }

  @RequestMapping(path = "/settings/owner", method = RequestMethod.POST)
  public String addOwner(@RequestParam String ownerName){
    todoService.saveOwner(new Owner(ownerName));
    return "redirect:/settings";
  }

  @RequestMapping(path = "/settings/project", method = RequestMethod.POST)
  public String addProject(@RequestParam String projectName){
    Setting setting = todoService.getSettingById(1L);
    if (setting == null){
      setting = new Setting();
    }
    setting.addProject(projectName);
    todoService.saveSetting(setting);
    return "redirect:/settings";
  }

  @RequestMapping(path = "/settings/context", method = RequestMethod.POST)
  public String addContext(@RequestParam String contextName){
    Setting setting = todoService.getSettingById(1L);
    if (setting == null){
      setting = new Setting();
    }
    setting.addContext(contextName);
    todoService.saveSetting(setting);
    return "redirect:/settings";
  }

  @Transactional
  @RequestMapping(path = "/settings/owners/{owner}", method = RequestMethod.POST)
  public String deleteOwner(@PathVariable String owner){
    todoService.deleteOwner(owner);
    return "redirect:/settings";
  }

  @Transactional
  @RequestMapping(path = "/settings/projects/{project}/delete", method = RequestMethod.POST)
  public String deleteProject(@PathVariable String project){
    todoService.deleteProject(project);
    return "redirect:/settings";
  }

  @Transactional
  @RequestMapping(path = "/settings/contexts/{context}/delete", method = RequestMethod.POST)
  public String deleteContext(@PathVariable String context){
    todoService.deleteContext(context);
    return "redirect:/settings";
  }
}