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

    model.addAttribute("owners", todoService.getOwnerNames());
    model.addAttribute("projects", todoService.getProjectNames());
    model.addAttribute("contexts", todoService.getContextNames());

    model.addAttribute("selectedOwner", owner);
    model.addAttribute("selectedProject", project);
    model.addAttribute("selectedContext", context);
    model.addAttribute("items", filteredTodos.size());

    return "main";
  }

  @RequestMapping(path = "/todo/{id}/edit", method = RequestMethod.GET)
  public String showEditForm(Model model, @PathVariable Long id) {
    Todo todo = todoService.getTodo(id);
    if(todo.getIsInstant()){
      String title = todo.getTitle();
      todo = createEmptyTodo();
      todo.setTitle(title);
      todo.setId(id);
      todo.setIsInstant(true);
    }
    model.addAttribute("displayMode", "edit");
    addTodoAttributes(model, todo);
    return "todo";
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
    model.addAttribute("displayMode", "add");
    addTodoAttributes(model, todo);
    return "todo";
  }

  // TODO: move to service
  private Todo createEmptyTodo() {
    Todo todo = new Todo("", "", "", "", null, Priority.LOW, Status.NOT_STARTED, new Owner("", ""));
    todo.setId(0L);
    return todo;
  }

  @RequestMapping(path = "/todo/add", method = RequestMethod.POST)
  public String addTodo(@ModelAttribute Todo todo) {
    todoService.saveTodo(todo);
    return "redirect:/";
  }

  @RequestMapping(path="/todo/add-instant", method = RequestMethod.POST)
  public String addInstantTodo(String title, Model model){
    // TODO: move this logic to service
    List<Owner> owners = new ArrayList();
    todoService.getOwners().forEach(o -> owners.add(o));
    Todo instantTodo = new Todo(title, "", "not set", "not set", LocalDate.now(), Priority.MUST, Status.NOT_STARTED, owners.get(0));
    instantTodo.setIsInstant(true);

    todoService.saveTodo(instantTodo);

    Todo todo = createEmptyTodo();
    addTodoAttributes(model, todo);
    model.addAttribute("instantTaskAdded", "ok");
    model.addAttribute("displayMode", "add");
    return "todo";
  }

  @RequestMapping(path = "/todo/id/{id}/owner/{owner}/project/{project}/context/{context}/delete", method = RequestMethod.GET)
  public String deleteTodo(@PathVariable Long id,
                           @PathVariable String owner,
                           @PathVariable String project,
                           @PathVariable String context, Model model) {
    todoService.deleteById(id);

    return getMainPage(model,owner, project,context);
  }

  @RequestMapping(path = "/todo/id/{id}/owner/{owner}/project/{project}/context/{context}/done", method = RequestMethod.GET)
  public String completeTodo(@PathVariable Long id,
                             @PathVariable String owner,
                             @PathVariable String project,
                             @PathVariable String context, Model model) {
    Todo todo = todoService.getTodo(id);
    todo.setStatus(Status.FINISHED);
    todoService.saveTodo(todo);
    return getMainPage(model,owner, project,context);
  }

  @RequestMapping(path = "/settings", method = RequestMethod.GET)
  public String showSettings(Model model) {
    // TODO: make it a query:
    List<String> ownerNames = new ArrayList();
    todoService.getOwners().forEach(o -> ownerNames.add(o.getName()));
    model.addAttribute("owners", ownerNames);
    model.addAttribute("projects", todoService.getProjects());
    model.addAttribute("contexts", todoService.getContexts());
    // model.addAttribute("showDone", todoService.getSettingById(1L).isShowDone());

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

  /*@Transactional
  @RequestMapping(path = "/settings/showdone", method = RequestMethod.POST)
  public String changeShowDone(boolean showDone){
    todoService.getSettingById(1L).setShowDone(showDone);
    return "redirect:/";
  }*/
}