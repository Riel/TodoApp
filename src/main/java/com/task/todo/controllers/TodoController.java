package com.task.todo.controllers;

import com.task.todo.models.Todo;
import com.task.todo.models.TodoDTO;
import com.task.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    TodoDTO dto = todoService.convertTodoToDTO(todo);
    model.addAttribute("todo", dto);
    model.addAttribute("owners", todoService.getOwners());
    model.addAttribute("priorities", todoService.getPriorities());
    model.addAttribute("statuses", todoService.getStatuses());
    model.addAttribute("contexts", todoService.getContexts());
    model.addAttribute("projects", todoService.getProjects());
    return "tododetail";
  }

  @RequestMapping(path = "/todo/{id}/edit", method = RequestMethod.POST)
  public String updateTodo(@PathVariable Long id, @ModelAttribute TodoDTO dto) {
    Todo todo = todoService.convertDTOToTodo(dto);
    todoService.saveTodo(todo);
    return "redirect:/";
  }
}