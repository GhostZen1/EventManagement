package edu.my.utem.ftmk.projecteventmanagement;

public class EventType {
    public int id;
    public String name;

    public EventType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
