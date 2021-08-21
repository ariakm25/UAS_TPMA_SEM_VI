package com.tugas.is3_10518122.model;

public class Todo {
    private int id;
    private String title;
    private String status;
    private String deadline;

    public Todo(String title, String deadline, String status) {
        this.title = title;
        this.deadline = deadline;
        this.status = status;
    }

    public Todo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
