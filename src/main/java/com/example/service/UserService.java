package com.example.service;

import com.example.DTO.PostUserDto;
import com.example.DTO.PutUserDto;
import com.example.model.Role;
import com.example.model.User;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public interface UserService {
    List<PostUserDto> getAllUsers();
    User findById(Long id);
    void addUser(PostUserDto postUserDto);
    void deleteUser(User user);
    void updateUser(PutUserDto putUserDto);
    User getUserByName(String name);
    PostUserDto getPostUserDtoByName(String name);
    PutUserDto getPutUserDtoByName(String name);
    void addUserRoles(Long userId, String userRoles);
    Role findByRole(String role);
    Role findByRole(Long id);
    Set<Role> findByRole(Set<?> roleId);
    List<Role> getAllRoles();
    String setStringRoles(User user);
    User convertFromDtoToEntity(PostUserDto postUserDto);
    User convertFromDtoToEntity(PutUserDto putUserDto);
    PostUserDto convertToPostUserDto(User user);
    PutUserDto convertToPutUserDto(User user);
    List<PostUserDto> convertToDtoList(List<User> users);
}
