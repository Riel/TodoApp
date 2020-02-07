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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TodoController {

  private TodoService todoService;


  @Autowired
  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String getMainPage(Model model,
                            @RequestParam (required = false) String owner,
                            @RequestParam (required = false) String project,
                            @RequestParam (required = false) String context){

    List<Todo> filteredTodos = todoService.getFilteredTodos(owner, project, context);
    model.addAttribute("todos", filteredTodos);

    // Todo: move this bloct to service:
    List<String> ownerNames = new ArrayList();
    todoService.getOwners().forEach(o -> ownerNames.add(o.getName()));
    ownerNames.add(todoService.getAllFilter());
    model.addAttribute("owners", ownerNames);
    List<String> projectNames = new ArrayList<>();
    todoService.getProjects().forEach(p->projectNames.add(p));
    projectNames.add(todoService.getAllFilter());
    model.addAttribute("projects", projectNames);
    List<String> contextNames = new ArrayList<>();
    todoService.getContexts().forEach(c->contextNames.add(c));
    contextNames.add(todoService.getAllFilter());
    model.addAttribute("contexts", contextNames);

    model.addAttribute("selectedOwner", owner);
    model.addAttribute("selectedProject", project);
    model.addAttribute("selectedContext", context);
    model.addAttribute("items", filteredTodos.size());

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

  @RequestMapping(path = "/todo/id/{id}/owner/{owner}/project/{project}/context/{context}/delete", method = RequestMethod.GET)
  public String deleteTodo(@PathVariable Long id,
                           @PathVariable String owner,
                           @PathVariable String project,
                           @PathVariable String context, Model model) {
    todoService.deleteById(id);

    return getMainPage(model,owner, project,context);
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
    todoService.saveOwner(new Owner(ownerName.trim()));
    return "redirect:/settings";
  }

  @RequestMapping(path = "/settings/project", method = RequestMethod.POST)
  public String addProject(@RequestParam String projectName){
    Setting setting = todoService.getSettingById(1L);
    if (setting == null){
      setting = new Setting();
    }
    setting.addProject(projectName.trim());
    todoService.saveSetting(setting);
    return "redirect:/settings";
  }

  @RequestMapping(path = "/settings/context", method = RequestMethod.POST)
  public String addContext(@RequestParam String contextName){
    Setting setting = todoService.getSettingById(1L);
    if (setting == null){
      setting = new Setting();
    }
    setting.addContext(contextName.trim());
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