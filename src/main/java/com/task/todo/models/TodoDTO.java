package com.task.todo.models;

public class TodoDTO {

  //region Fields
  private Long id;
  private String title;
  private String description;
  private String project;
  private String context;
  private String creationDate;
  private String dueDate;
  private String prio;
  private String status;
  private String owner;
  //endregion

  public TodoDTO(){
  }

  public TodoDTO(Long id, String title, String description, String project, String context, String creationDate, String dueDate, String prio, String status, String owner) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.project = project;
    this.context = context;
    this.creationDate = creationDate;
    this.dueDate = dueDate;
    this.prio = prio;
    this.status = status;
    this.owner = owner;
  }

  //region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public String getPrio() {
    return prio;
  }

  public void setPrio(String prio) {
    this.prio = prio;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }
  //endregion
}
