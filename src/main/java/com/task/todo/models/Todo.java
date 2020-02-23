package com.task.todo.models;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "todos")
public class Todo implements Comparable<Todo> {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;

  // @Column(columnDefinition = "TEXT")
  private String description;
  private String link;
  private String project;
  private String context;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate creationDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dueDate;
  // @DateTimeFormat(pattern = "yyyy-MM-dd")
  // private LocalDate completionDate; // TODO: getter, setter
  private Priority prio;
  private Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private Owner owner;
  //endregion


  public Todo() {
    creationDate = LocalDate.now();
  }

  public Todo(String title, String description, String project, String context, LocalDate dueDate, Priority prio, Status status, Owner owner) {
    this(title, description, project, context, dueDate, prio, status);
    this.owner = owner;
    creationDate = LocalDate.now();
  }

  // TODO: remove if not used!
  public Todo(String title, String description, String project, String context, LocalDate dueDate, Priority prio, Status status) {
    this.title = title;
    this.description = description;
    this.project = project;
    this.context = context;
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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link.equals("") ? null :link;
  }

  public boolean hasLink() {
    return link != null;
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

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
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

  public String getDisplayDueDate() {
    return dueDate == null ? "-" : Utilities.dateToString(dueDate);
  }

  public String getDisplayCreationDate() {
    return Utilities.dateToString(creationDate);
  }

  @Override
  public int compareTo(Todo o) {
    if (prio.ordinal() < o.getPrio().ordinal()){
      return -1;
    } else if (prio.ordinal() == o.getPrio().ordinal()){
      return 0;
    } else {
      return 1;
    }
  }
}