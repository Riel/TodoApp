package com.task.todo.enums;

public enum Priority {
  MUST("Must"),
  HIGH("High"),
  MEDIUM("Medium"),
  LOW("Low"),
  WAIT("Waiting");

  private final String displayName;

  Priority(final String display) {
    this.displayName = display;
  }

  @Override
  public String toString() {
    return this.displayName;
  }
}