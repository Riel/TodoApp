package com.task.todo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Setting {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private ArrayList<String> contexts = new ArrayList<>();
  private ArrayList<String> projects = new ArrayList<>();
  private boolean showDone;
  //endregion

  //region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isShowDone() {
    return showDone;
  }

  public void setShowDone(boolean showDone) {
    this.showDone = showDone;
  }

  public ArrayList<String> getContexts() {
    return contexts;
  }

  public ArrayList<String> getProjects() {
    return projects;
  }
  //endregion

  // TODO: might need to be replaced with a setter if not used
  public void addContext(String context) {
    contexts.add(context);
  }

  public void addProject(String project) {
    projects.add(project);
  }
}