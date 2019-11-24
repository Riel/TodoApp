package com.task.todo.services;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Todo;
import com.task.todo.models.TodoDTO;
import com.task.todo.models.Utilities;

import java.text.ParseException;
import java.util.Date;

public class TodoConverter {

  public Todo convertDTOToTodo(TodoDTO dto, Date creationDate){

    Date dueDate;

    // TODO: make this legal:
    try{
      dueDate = Utilities.stringToDate(dto.getDueDate());
    } catch (ParseException e) {
      dueDate = new Date();
    }

    Todo todo =new Todo(
            dto.getTitle(),
            dto.getDescription(),
            dto.getProject(),
            dto.getContext(),
            creationDate,
            dueDate,
            Priority.getByDisplayName(dto.getPrio()),
            Status.getByDisplayName(dto.getStatus()));

    todo.setId(dto.getId());

    return todo;
  }

  public TodoDTO concertDTOToDTO (Todo todo){
    TodoDTO dto =new TodoDTO(
            todo.getId(),
            todo.getTitle(),
            todo.getDescription(),
            todo.getProject(),
            todo.getContext(),
            todo.getDisplayCreationDate(),
            todo.getDisplayDueDate(),
            todo.getPrio().toString(),
            todo.getStatus().toString(),
            todo.getOwner().getName());

    return dto;
  }
}
