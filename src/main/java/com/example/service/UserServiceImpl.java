package com.example.service;

import com.example.DAO.UserDao;
import com.example.DTO.PostUserDto;
import com.example.DTO.PutUserDto;
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
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private UserRolesDao userRolesDao;

    @Override
    @Transactional
    public List<PostUserDto> getAllUsers() {
        List<User> usersList = userDao.getAllUsers();
        return usersList.stream().map(user -> {
            PostUserDto postUserDto = new PostUserDto();
            postUserDto.setId(user.getId());
            postUserDto.setLogin(user.getLogin());
            postUserDto.setPassword(user.getPassword());
            postUserDto.setStringRoles(setStringRoles(user));
            return postUserDto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public void addUser(PostUserDto postUserDto) {
        User user = new User();
        user.setLogin(postUserDto.getLogin());
        user.setPassword(postUserDto.getPassword());
        Set<Role> rolesSet = roleDao.findByRole(postUserDto.getRoleIds());
        user.setRoles(rolesSet);
    if (!userDao.isExistUser(user.getLogin()))
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    @Transactional
    public void updateUser(PutUserDto putUserDto) {
        User user = new User();
        user.setId(putUserDto.getId());
        user.setLogin(putUserDto.getLogin());
        user.setPassword(putUserDto.getPassword());
        Set<Role> rolesSet = roleDao.findByRole(putUserDto.getRoleIds());
        user.setRoles(rolesSet);
        userDao.updateUser(user);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    @Transactional
    public PostUserDto getPostUserDtoByName(String name)
    {
        User user = userDao.getUserByName(name);
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setId(user.getId());
        postUserDto.setLogin(user.getLogin());
        postUserDto.setPassword(user.getPassword());
        postUserDto.setStringRoles(setStringRoles(user));
        return postUserDto;
    }

    @Override
    @Transactional
    public PutUserDto getPutUserDtoByName(String name)
    {
        User user = userDao.getUserByName(name);
        PutUserDto putUserDto = new PutUserDto();
        putUserDto.setId(user.getId());
        putUserDto.setLogin(user.getLogin());
        putUserDto.setPassword(user.getPassword());
        putUserDto.setStringRoles(setStringRoles(user));
        return putUserDto;
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

    public User convertFromDtoToEntity(PostUserDto postUserDto) {
        User user = new User();
        user.setLogin(postUserDto.getLogin());
        user.setPassword(postUserDto.getPassword());
        user.setRoles(findByRole(postUserDto.getRoleIds()));
        return user;
    }

    @Override
    public User convertFromDtoToEntity(PutUserDto putUserDto) {
        User user = new User();
        user.setId(putUserDto.getId());
        user.setLogin(putUserDto.getLogin());
        user.setPassword(putUserDto.getPassword());
        user.setRoles(findByRole(putUserDto.getRoleIds()));
        return user;
    }

    public PostUserDto convertToPostUserDto(User user) {
        PostUserDto postUserDto = new PostUserDto(user.getId(),
                                                    user.getLogin(),
                                                    user.getPassword());
        postUserDto.setStringRoles(setStringRoles(user));
        return postUserDto;
    }

    @Override
    public PutUserDto convertToPutUserDto(User user) {
        PutUserDto putUserDto = new PutUserDto(user.getId(),
                                                user.getLogin(),
                                                user.getPassword());
        putUserDto.setStringRoles(setStringRoles(user));
        return putUserDto;
    }

    public List<PostUserDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(this::convertToPostUserDto)
                .collect(Collectors.toList());
    }
}
