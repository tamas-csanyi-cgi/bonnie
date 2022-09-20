package com.cgi.hexagon.h2storage.user;

import com.cgi.hexagon.businessrules.Role;
import com.cgi.hexagon.h2storage.order.AssemblyOrder;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AssemblyUser {

    @Id
    @GeneratedValue
    long userId;

    String name;

    String password;

    Role role;

    @OneToMany(targetEntity = AssemblyOrder.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "asd", referencedColumnName = "user_id")
    private Set<AssemblyOrder> orders;

    public Set<AssemblyOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<AssemblyOrder> orders) {
        this.orders = orders;
    }

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

    public AssemblyUser withOrder(Set<AssemblyOrder> orders) {
        this.orders = orders;
        return this;
    }

    public long getId() {
        return userId;
    }

    public void setId(long userId) {
        this.userId = userId;
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
