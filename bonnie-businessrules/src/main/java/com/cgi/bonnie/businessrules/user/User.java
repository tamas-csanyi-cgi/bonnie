package com.cgi.bonnie.businessrules.user;

import com.cgi.bonnie.businessrules.Role;

public class User {

    Long id;

    String name;

    Role role;

    String email;

    public User(long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User withId(Long id) {
        this.setId(id);
        return this;
    }

    public User withName(String name) {
        this.setName(name);
        return this;
    }

    public User withEmail(String email) {
        this.setEmail(email);
        return this;
    }

    public User withRole(Role role) {
        this.setRole(role);
        return this;
    }
}
