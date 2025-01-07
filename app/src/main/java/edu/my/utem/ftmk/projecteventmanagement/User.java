package edu.my.utem.ftmk.projecteventmanagement;

public class User {
    public int id;
    public String name;
    public String password;
    public String email;
    public String ic;
    public String role;

    public User(String name, String password, String email, String ic, String role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.ic = ic;
        this.role = role;
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
    public String getIc() {
        return ic;
    }
    public String getRole() {
        return role;
    }
}
