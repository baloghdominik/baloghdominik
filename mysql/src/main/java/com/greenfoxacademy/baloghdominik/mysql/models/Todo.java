package com.greenfoxacademy.baloghdominik.mysql.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String title;
    private boolean urgent;
    private boolean done;

    public Todo(String title){
        this.title = title;
        this.done = false;
        this.urgent = false;
    }

    public Todo(){

    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getTitle() {
        return title;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public boolean isDone() {
        return done;
    }

    public Long getId() {
        return id;
    }
}
