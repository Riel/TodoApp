package com.task.todo.controllers;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Owner;
import com.task.todo.models.Todo;
import com.task.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;

@Controller
public class TodoController {

  private TodoService todoService;

  @Autowired
  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String getMainPage(Model model){
    model.addAttribute("todos", todoService.getAllTodo());
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
    Todo todo = new Todo("", "", "", "", LocalDate.now(), LocalDate.now(), Priority.LOW, Status.NOT_STARTED, new Owner("", ""));
    todo.setId(0L);
    return todo;
  }

  private void addTodoAttributes(Model model, Todo todo) {
    model.addAttribute("todo", todo);
    model.addAttribute("owners", todoService.getOwners());
    model.addAttribute("priorities", Priority.values());
    model.addAttribute("statuses", Status.values());
    model.addAttribute("contexts", todoService.getContexts());
    model.addAttribute("projects", todoService.getProjects());
  }

  @RequestMapping(path = "/todo/add", method = RequestMethod.POST)
  public String addTodo(@ModelAttribute Todo todo) {
    todoService.saveTodo(todo);
    return "redirect:/";
  }

  @RequestMapping(path = "/todo/{id}/delete", method = RequestMethod.POST)
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
}