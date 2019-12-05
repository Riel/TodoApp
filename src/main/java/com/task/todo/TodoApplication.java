package com.task.todo;

import com.task.todo.models.Owner;
import com.task.todo.models.Setting;
import com.task.todo.repositories.SettingsRepository;
import com.task.todo.repositories.OwnerRepository;
import com.task.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    /*Owner a = new Owner();
    a.setEmail("riel@riel.hu");
    a.setName("Riel");

    Owner b = new Owner();
    b.setEmail("nelita@nelita.hu");
    b.setName("Nelita");

    ownerRepository.save(a);
    ownerRepository.save(b);

    Setting s = new Setting();
    s.addContext("Offline");
    s.addContext("Shopping");
    s.addContext("Computer");
    s.addContext("Online");
    s.addProject("GFA");
    s.addProject("KD");
    s.addProject("Home");

    settingRepository.save(s);*/
  }
}