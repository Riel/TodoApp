package com.task.todo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;

  @OneToMany(mappedBy = "owner",
          fetch = FetchType.LAZY,
          cascade = CascadeType.PERSIST)
  private List<Todo> todos;


  //endregion

  public Owner (){
    todos = new ArrayList<>();
  }

  public Owner (String name){
    todos = new ArrayList<>();
    this.name = name;
  }

  public Owner(String name, String email) {
    todos = new ArrayList<>();
    this.name = name;
    this.email = email;
  }

  //region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Todo> getTodos() {
    return todos;
  }
  //endregion

  public void addTodo(Todo newTodo) {
    newTodo.setOwner(this);
    todos.add(newTodo);
  }
}