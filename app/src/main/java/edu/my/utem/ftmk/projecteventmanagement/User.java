package edu.my.utem.ftmk.projecteventmanagement;

public class User {
    public int id;
    public String name;
    public String password;
    public String email;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
