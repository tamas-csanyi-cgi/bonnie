package com.cgi.bonnie.bonnierest.model;

public class UserRequest {

    private String name;

    private String email;

    private String password;

    private String role;

    public UserRequest(String name, String password, String role, String email) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public UserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
