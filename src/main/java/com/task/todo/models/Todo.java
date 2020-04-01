package com.task.todo.models;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.utilities.TodoColors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

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
    this.link = link.equals("") ? null : link;
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

  public String getDisplayDueDateColor(){
    if (dueDate == null) {
      return TodoColors.GRAY;
    }

    Integer daysToFinish = Period.between(LocalDate.now(), dueDate).getDays();

    if (daysToFinish < 0) {            // should be already done
      return TodoColors.RED;
    } else if (daysToFinish == 0) {   // must be done today
      return TodoColors.ORANGE;
    } else if (daysToFinish == 1) {   // must be done tomorrow
      return TodoColors.YELLOW;
    } else if (daysToFinish == 2) {   // must be done day after tomorrow
      return TodoColors.LIME;
    } else {                          // can be done later
      return TodoColors.GREEN;
    }
  }

  public String getDisplayDueDateColorOld() {

    if (dueDate == null) {
      return "color: gray";
    }

    Integer daysToFinish = Period.between(LocalDate.now(), dueDate).getDays();
    System.out.println("Title: " + title);
    System.out.println("Days: " + daysToFinish);
    if (daysToFinish < 0) {            // should be already done
      //return getRGBColorString(220,79,13);
      //return getRGBColorString(248, 124, 97);
      //return getRGBColorString(159, 62, 16);
      //return getRGBColorString(71, 7, 42);
      //return getRGBColorString(255, 71, 22);
      return getRGBColorString(255, 105, 97);
      //return "color: red";
    } else if (daysToFinish == 0) {   // must be done today
      //return getRGBColorString(236,152,21);
      //return getRGBColorString(244, 193, 114);
      //return getRGBColorString(209, 149, 55);
      //return getRGBColorString(141, 94, 3);
      //return getRGBColorString(255,165,0);
      return getRGBColorString(255, 179, 85);
      //return "color: orange";
    } else if (daysToFinish == 1) {   // must be done tomorrow
      //return getRGBColorString(249,219,68);
      //return getRGBColorString(248, 229, 134);
      //return getRGBColorString(216,215,83);
      //return getRGBColorString(216, 215, 83);
      //return getRGBColorString(255, 246, 80);
      return getRGBColorString(253, 253, 150);
      //return "color: yellow";
    } else if (daysToFinish == 2) {
      //return getRGBColorString(165,212,83);
      //return getRGBColorString(210, 241, 154);
      //return getRGBColorString(103, 123, 20);
      //return getRGBColorString(25, 255, 200);
      //return getRGBColorString(211, 221, 24);
      return getRGBColorString(207, 240, 124);
      //return "color: lime";
    } else {                          // can be done later
      return "color: green";
    }
  }

  public String getRGBColorString(int r, int g, int b){
    return "color: rgb(" + r + "," + g + "," + b + ")";
  }

  @Override
  public int compareTo(Todo o) {
    if (prio.ordinal() < o.getPrio().ordinal()) {
      return -1;
    } else if (prio.ordinal() == o.getPrio().ordinal()) {
      return 0;
    } else {
      return 1;
    }
  }
}