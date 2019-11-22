package com.task.todo;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Owner;
import com.task.todo.models.Todo;
import com.task.todo.repositories.OwnerRepository;
import com.task.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TodoApplication implements CommandLineRunner {

  private OwnerRepository ownerRepository;
  private TodoRepository todoRepository;

  @Autowired
  public TodoApplication(OwnerRepository ownerRepository, TodoRepository todoRepository) {
    this.ownerRepository = ownerRepository;
    this.todoRepository = todoRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Owner a = new Owner();
    a.setEmail("riel@gmail.hu");
    a.setName("riel");

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    Date dueDate = format.parse("2012/05/13");
    Todo t = new Todo("Call Nelita", "Ask her about the presentation", dueDate, Priority.LOW, Status.NOT_STARTED);

    a.addTodo(t);
    ownerRepository.save(a);

    Iterable<Owner> restored = ownerRepository.findAll();
    System.out.println(restored);
  }
}
