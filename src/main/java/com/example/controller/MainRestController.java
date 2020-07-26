package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rest")
public class MainRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private Gson gson;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("admin/update")
    public ResponseEntity<User> editUser(String user, String roleId) {
        User userFromJson = gson.fromJson(user, User.class);
        Set<Long> roleListFromJson = gson.fromJson(roleId, HashSet.class);
        userFromJson.setRoles(userService.findByRole(roleListFromJson));
        userFromJson.setStringRoles(userService.setStringRoles(userFromJson));
        userService.updateUser(userFromJson);
        return new ResponseEntity<>(userFromJson, HttpStatus.OK);
    }

    @PostMapping("/admin/adduser")
    public ResponseEntity<User> addUser(String user, String roleId) {
        User userFromJson = gson.fromJson(user, User.class);
        Set<Long> roleListFromJson = gson.fromJson(roleId, HashSet.class);
        userFromJson.setRoles(userService.findByRole(roleListFromJson));
        userFromJson.setStringRoles(userService.setStringRoles(userFromJson));
        userService.addUser(userFromJson);
        return new ResponseEntity<>(userFromJson, HttpStatus.OK);
    }

    @PostMapping("/admin/delete")
    public ResponseEntity<String> deleteUser(String id) {
        userService.deleteUser(userService.findById(Long.parseLong(id)));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
