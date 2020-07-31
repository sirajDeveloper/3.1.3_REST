package com.example.controller;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.model.User;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    /*@GetMapping("user")
    public String user(ModelMap modelMap, Authentication auth) {
        if (auth.isAuthenticated()) {
            String userName = auth.getName();
            User user = userService.getUserByName(userName);
            modelMap.addAttribute("user", user);
        }
        return "user"; 
    }*/

    @GetMapping("admin")
    public String showUsersTable(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
        }
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    /*@GetMapping("signup")
    public String showSignUpForm(User user, ModelMap model) {
        return "add-user";
    }

    @PostMapping("adduser")
    public String addUser(@RequestParam(value = "role_id") Set<Long> roleId,
                          @Validated User user, BindingResult result,
                          ModelMap model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        user.setRoles(userService.findByRole(roleId));
        *//*user.setStringRoles(userService.setStringRoles(user));*//*
        userService.addUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }


    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("update/{id}")
    public String updateUser(@RequestParam(value = "role_id") Set<Long> roleId,
                             @PathVariable("id") long id, @Validated User user,
                             BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }
        user.setRoles(userService.findByRole(roleId));
        userService.updateUser(user);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model, HttpSession session) {
        User user = userService.findById(id);
        userService.deleteUser(user);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }*/
}
