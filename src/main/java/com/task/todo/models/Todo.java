package com.task.todo.models;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
public class Todo {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private String project;
  private String context;
  private Date creationDate;
  private Date dueDate;
  private Priority prio;
  private Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private Owner owner;
  //endregion


  public Todo() {
    creationDate = new Date();
  }

  public Todo(String title, String description, String project, String context, Date creationDate, Date dueDate, Priority prio, Status status, Owner owner) {
    this(title, description, project, context, creationDate, dueDate, prio, status);
    this.owner = owner;
  }

  public Todo(String title, String description, String project, String context, Date creationDate,  Date dueDate, Priority prio, Status status) {
    this.title = title;
    this.description = description;
    this.project = project;
    this.context = context;
    this.creationDate = creationDate;
    this.dueDate = dueDate;
    this.prio = prio;
    this.status = status;
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

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public Priority getPrio() {
    return prio;
  }

  public void setPrio(Priority prio) {
    this.prio = prio;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }
  //endregion

  public String getDisplayDueDate(){
    return Utilities.dateToString(dueDate);
  }

  public String getDisplayCreationDate(){
    return Utilities.dateToString(creationDate);
  }
}