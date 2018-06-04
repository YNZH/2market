package com.gjf.security;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: GJF
 * @Date : 2018/06/04
 * Time   : 20:58
 */
public class TUser {
    private Long id;
    private String name;
    private Set<String> roles = new HashSet<>();
    private Set<String> permissions = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addRoles(String role) {
        this.roles.add(role);
    }

    public void addPermissions(String permission) {
        this.permissions.add(permission);
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
