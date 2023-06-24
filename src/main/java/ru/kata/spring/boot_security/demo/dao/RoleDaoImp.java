package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Set<Role> findRoles(List<Long> roles) {
        TypedQuery<Role> q = entityManager.createQuery("select r from Role r where r.id in :role", Role.class);
        q.setParameter("role", roles);
        return new HashSet<>(q.getResultList());

    }

    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r").getResultList();
    }

    public void addDefaultRoles() {
        List<Long> roleIds = List.of(1L, 2L);

        Set<Role> existingRoles = findRoles(roleIds);
        boolean hasUserRole = false;
        boolean hasAdminRole = false;

        for (Role role : existingRoles) {
            if (role.getName().equals("ROLE_USER")) {
                hasUserRole = true;
            }
            if (role.getName().equals("ROLE_ADMIN")) {
                hasAdminRole = true;
            }
        }

        if (!hasUserRole) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            entityManager.persist(roleUser);
        }

        if (!hasAdminRole) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            entityManager.persist(roleAdmin);
        }
    }
}
