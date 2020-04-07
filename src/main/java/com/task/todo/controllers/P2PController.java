package com.task.todo.controllers;

import com.task.todo.models.Result;
import com.task.todo.models.Todo;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;

@RestController
public class P2PController {

  @RequestMapping(path = "/getresult", method = RequestMethod.GET)
  public Result updateTodo(@PathVariable Long id, @ModelAttribute Todo todo) {
    return new Result("valami");
  }
}
