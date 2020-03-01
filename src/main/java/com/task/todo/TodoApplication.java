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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    //initializeDatabase();
  }

  private void initializeDatabase(){
    Owner a = new Owner();
    a.setEmail("riel@riel.hu");
    a.setName("Riel");

    Owner b = new Owner();
    b.setEmail("nelita@nelita.hu");
    b.setName("Nelita");

    String p1 = "GFA";
    String p2 = "Home";
    String p3 = "Move to Hungary";
    String p4 = "Todo app";

    String c1 = "Computer";
    String c2 = "Home";
    String c3 = "Workplace";
    String c4 = "Phone";

    for (int j = 0; j < 20; j++) {
      for (int i = 0; i < 4; i++) {
        String name = "Name for id " + i;
        String desc = "Description for the task: " + name;
        String project;
        String context;
        Priority prio;
        Status stat;
        Owner o;
        if (i == 0){
          project = p1;
          context = c1;
          prio = Priority.MEDIUM;
          stat = Status.BLOCKED;
          o = a;
        } else if (i == 1){
          project = p2;
          context = c2;
          prio = Priority.HIGH;
          stat = Status.NOT_STARTED;
          o = b;
        } else if (i == 2){
          project = p3;
          context = c3;
          prio = Priority.MUST;
          stat = Status.PROGRESS;
          o = a;
        } else {
          project = p4;
          context = c4;
          prio = Priority.LOW;
          stat = Status.ON_HOLD;
          o = b;
        }

        LocalDate due = LocalDate.now();
        Todo t = new Todo(name, desc, project, context, due, prio, stat, o);
        o.addTodo(t);
      }
    }

    ownerRepository.save(a);
    ownerRepository.save(b);

    Setting s = new Setting();
    s.addContext(c1);
    s.addContext(c2);
    s.addContext(c3);
    s.addContext(c4);

    s.addProject(p1);
    s.addProject(p2);
    s.addProject(p3);
    s.addProject(p4);

    settingRepository.save(s);
  }
}