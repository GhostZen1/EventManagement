package edu.my.utem.ftmk.projecteventmanagement;

public class EventType {
    public int id;
    public String name;
    public String imageName;

    public EventType(int id, String name, String imageName) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getImageName() {
        return imageName;
    }
}
