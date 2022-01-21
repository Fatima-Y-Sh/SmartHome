package com.smarthome.users;

public class User {
    protected String username;
    protected String password;
    protected boolean privileges;

    public User(String username, String password, boolean privileges) {
        this.username = username;
        this.password = password;
        this.privileges = privileges;
    }

    public String getUsername() {
        return username;
    }
}
