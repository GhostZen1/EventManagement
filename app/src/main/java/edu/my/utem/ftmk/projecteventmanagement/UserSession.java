package edu.my.utem.ftmk.projecteventmanagement;

public class UserSession {
    private int userId;
    private String role;

    public UserSession(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
