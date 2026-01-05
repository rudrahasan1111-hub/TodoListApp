package com.example.todolistapp;

public class TodoItem {
    private int id;
    private String task;

    // Constructor for new tasks (id is auto-assigned by the database)
    public TodoItem(String task) {
        this.task = task;
    }

    // Constructor with id (for reading existing tasks)
    public TodoItem(int id, String task) {
        this.id = id;
        this.task = task;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }
}
