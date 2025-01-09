package edu.my.utem.ftmk.projecteventmanagement;

public class UserAdmin {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String ic;
    private String role;

    public UserAdmin(int userId, String username, String password, String email, String ic, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.ic = ic;
        this.role = role;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getIc() { return ic; }
    public void setIc(String ic) { this.ic = ic; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

