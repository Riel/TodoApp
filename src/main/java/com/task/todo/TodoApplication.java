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

import java.text.SimpleDateFormat;
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
    a.setName("Endre");

    Owner b = new Owner();
    b.setEmail("ferenc@gmail.hu");
    b.setName("Ferenc");

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    Date dueDate = format.parse("2019/12/03");
    Date date = format.parse(format.format(dueDate));

    Date dueDate2 = format.parse("2019/12/13");
    Date date2 = format.parse(format.format(dueDate2));

    Todo t = new Todo("Write todo app", "Make it look nice", "Office", "Offline", date, Priority.HIGH, Status.PROGRESS);
    Todo t2 = new Todo("Cook cake", "Make it taste good", "Home", "Online", date2, Priority.LOW, Status.NOT_STARTED);

    a.addTodo(t);
    ownerRepository.save(a);
    b.addTodo(t2);
    ownerRepository.save(b);

    Iterable<Owner> restored = ownerRepository.findAll();
    System.out.println(restored);


    Setting s = new Setting();
    s.addContext("Offline");
    s.addContext("Online");
    s.addContext("With Thomas");
    s.addProject("GFA");
    s.addProject("Office");
    s.addProject("Home");

    settingRepository.save(s);
  }
}