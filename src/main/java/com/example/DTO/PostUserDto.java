package com.example.DTO;

import com.example.model.User;

import java.util.Set;

public class PostUserDto {
    private Long id;
    private String login;
    private String password;
    private String stringRoles;
    private Set<Long> roleIds;

    public PostUserDto() {
    }

    public PostUserDto(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStringRoles() {
        return stringRoles;
    }

    public void setStringRoles(String stringRoles) {
        this.stringRoles = stringRoles;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
