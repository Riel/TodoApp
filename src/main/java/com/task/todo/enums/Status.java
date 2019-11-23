package com.task.todo.enums;

public enum Status {
  NOT_STARTED("Not started"),
  PROGRESS("In progress"),
  ON_HOLD("On hold"),
  BLOCKED("Blocked"),
  FINISHED("Done");

  private final String displayName;

  Status(final String display) {
    this.displayName = display;
  }

  @Override
  public String toString() {
    return this.displayName;
  }
}
