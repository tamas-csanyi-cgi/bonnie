package com.cgi.bonnie.businessrules.user;

import com.cgi.bonnie.businessrules.Role;

public class User {

    long id;

    String name;

    Role role;

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

    public User withRole(Role role) {
        this.setRole(role);
        return this;
    }
}
