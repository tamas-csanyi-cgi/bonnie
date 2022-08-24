package com.cgi.hexagon.businessrules.user;

import com.cgi.hexagon.businessrules.Role;

import java.util.UUID;

public class User {

    long id;

    String name;

    String password;

    Role role;

    public User(long id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User withId(long id) {
        this.setId(id);
        return this;
    }

    public User withName(String name) {
        this.setName(name);
        return this;
    }

    public User withPassword(String password) {
        this.setPassword(password);
        return this;
    }

    public User withRole(Role role) {
        this.setRole(role);
        return this;
    }
}
