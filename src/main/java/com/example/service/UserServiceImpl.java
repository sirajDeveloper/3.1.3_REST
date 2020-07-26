package com.example.service;

import com.example.DAO.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.DAO.RoleDao;
import com.example.DAO.UserDaoImpl;
import com.example.DAO.UserRolesDao;
import com.example.model.Role;
import com.example.model.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private UserRolesDao userRolesDao;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
    if (!userDao.isExistUser(user.getUsername()))
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    @Transactional
    public void addUserRoles(Long userId, String userRoles) {
        userRolesDao.addUserRoles(userId, userRoles);
    }

    @Override
    @Transactional
    public Role findByRole(String role) {
        return roleDao.findByRole(role);
    }

    @Override
    @Transactional
    public Role findByRole(Long id) {
        return roleDao.findByRole(id);
    }

    @Override
    @Transactional
    public Set<Role> findByRole(Set<?> roleId) {
        return roleDao.findByRole(roleId);
    }

    @Override
    @Transactional
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public String setStringRoles(User user) {
        StringBuilder sb = new StringBuilder();
        user.getRoles().forEach(role -> {
            if (role.equals(roleDao.findByRole(2L))) {
                sb.append("ADMIN");
            } else {
                sb.append("USER");
            }
            sb.append(", ");
        });
        sb.deleteCharAt(sb.length()-2);
        return sb.toString();
    }
}
