package com.example.controller;

import com.example.DTO.PostUserDto;
import com.example.DTO.PutUserDto;
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
    public ResponseEntity<List<PostUserDto>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(userService.convertToDtoList(users));
    }

    @PutMapping("admin/update")
    public ResponseEntity<PutUserDto> editUser(@RequestBody PutUserDto putUserDto) {
        User user = userService.convertFromDtoToEntity(putUserDto);
        userService.updateUser(user);
        return ResponseEntity.ok().body(userService
                .convertToPutUserDto(userService.findById(user.getId())));
    }

    @PostMapping("/admin/adduser")
    public ResponseEntity<PostUserDto> addUser(@RequestBody PostUserDto postUserDto) {
        User user = userService.convertFromDtoToEntity(postUserDto);
        userService.addUser(user);
        return ResponseEntity.ok().body(userService
                .convertToPostUserDto(userService.findById(user.getId())));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        userService.deleteUser(userService.findById(id));
        return ResponseEntity.ok().body(id);
    }
}
