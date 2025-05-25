package org.example.mrdverkin.dto;

public class SessionStatus {
    private boolean loggedIn;
    private String role;

    public SessionStatus(boolean loggedIn, String role) {
        this.loggedIn = loggedIn;
        this.role = role;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getRole() {
        return role;
    }
}

