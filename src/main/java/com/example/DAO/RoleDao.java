package com.example.DAO;

import com.example.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Role findByRole(String role);
    Role findByRole(Long id);
    Set<Role> findByRole(Set<?> roleId);
    List<Role> getAllRoles();
}
