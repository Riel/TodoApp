package com.task.todo;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Owner;
import com.task.todo.models.Setting;
import com.task.todo.models.Todo;
import com.task.todo.repositories.SettingsRepository;
import com.task.todo.repositories.OwnerRepository;
import com.task.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootApplication
public class TodoApplication implements CommandLineRunner {

  // TODO: remove these
  private OwnerRepository ownerRepository;
  private TodoRepository todoRepository;
  private SettingsRepository settingRepository;

  @Autowired
  public TodoApplication(OwnerRepository ownerRepository, TodoRepository todoRepository, SettingsRepository settingRepository) {
    this.ownerRepository = ownerRepository;
    this.todoRepository = todoRepository;
    this.settingRepository = settingRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Owner a = new Owner();
    a.setEmail("endre@gmail.hu");
    a.setName("Riel");

    Owner b = new Owner();
    b.setEmail("ferenc@gmail.hu");
    b.setName("Barna");

    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dueDate = LocalDate.parse("2019-12-03", outputFormatter);
    LocalDate date = LocalDate.now();

    LocalDate dueDate2 = LocalDate.parse("2019-12-13", outputFormatter);
    LocalDate date2 = LocalDate.now();

    Todo t = new Todo("Update todo app style", "Make it look nice", "KD", "Online", date2, dueDate2, Priority.HIGH, Status.PROGRESS);
    Todo t2 = new Todo("Finalize orientation exam", "Make it taste good", "GFA", "Online", date, dueDate, Priority.LOW, Status.NOT_STARTED);
    Todo t3 = new Todo("Make new invitation", "With dates starting from 2020 February", "KD", "Computer", date, dueDate, Priority.LOW, Status.NOT_STARTED);

    a.addTodo(t);
    a.addTodo(t3);
    ownerRepository.save(a);
    b.addTodo(t2);

    ownerRepository.save(b);


    Iterable<Owner> restored = ownerRepository.findAll();
    Owner o = ownerRepository.findById(1L).get();
    System.out.println(restored);


    Setting s = new Setting();
    s.addContext("Offline");
    s.addContext("Shopping");
    s.addContext("Computer");
    s.addContext("Online");
    s.addProject("GFA");
    s.addProject("KD");
    s.addProject("Home");

    settingRepository.save(s);
  }
}