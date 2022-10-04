package com.cgi.bonnie.h2storage.user;

import com.cgi.bonnie.businessrules.Role;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class AssemblyUser implements Serializable {

    @Id
    private long id;

    private String name;

    private String password;

    private Role role;

    public AssemblyUser() {
    }

    public AssemblyUser withId(long id) {
        this.setId(id);
        return this;
    }

    public AssemblyUser withName(String name) {
        this.setName(name);
        return this;
    }

    public AssemblyUser withPassword(String password) {
        this.setPassword(password);
        return this;
    }

    public AssemblyUser withRole(Role role) {
        this.setRole(role);
        return this;
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
}
