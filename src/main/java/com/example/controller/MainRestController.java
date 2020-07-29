package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rest")
public class MainRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("admin/update")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        user.setRoles(userService.findByRole(user.getRoleIds()));
        user.setStringRoles(userService.setStringRoles(user));
        userService.updateUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/admin/adduser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        user.setRoles(userService.findByRole(user.getRoleIds()));
        user.setStringRoles(userService.setStringRoles(user));
        userService.addUser(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        userService.deleteUser(userService.findById(id));
        return ResponseEntity.ok().body(id);
    }
}
