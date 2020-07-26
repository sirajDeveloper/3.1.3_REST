package com.example.DAO;

import com.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    EntityManager entityManager;
    @Override
    public Role findByRole(String role) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where role = :role", Role.class);
        query.setParameter("role", role);
        return query.getSingleResult();
    }

    @Override
    public Role findByRole(Long id) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where id = :id", Role.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Set<Role> findByRole(Set<?> roleId) {
        Set<Role> roleSet = new HashSet<>();
        TypedQuery<Role> query = entityManager.createQuery("from Role where id = :id", Role.class);
        roleId.forEach(role -> {
            query.setParameter("id", Long.parseLong((String) role));
            roleSet.add(query.getSingleResult());});
        return roleSet;
    }

    @Override
    public List<Role> getAllRoles() {
        TypedQuery<Role> query =  entityManager.createQuery("from Role", Role.class);
        return query.getResultList();
    }

}
