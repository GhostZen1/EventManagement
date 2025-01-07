package edu.my.utem.ftmk.projecteventmanagement;

public class Event {

    private int id;
    private String name;
    private String date;
    private String category;
    private double price;

    public Event(int id, String name, String date, String category, Double price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public String getCategory() {
        return category;
    }
    public double getPrice() {
        return price;
    }
}
