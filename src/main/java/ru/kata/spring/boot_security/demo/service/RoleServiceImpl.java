package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> findRoles(List<Long> roles) {
        return roleRepository.findAllById(roles).stream().collect(Collectors.toSet());
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
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
            roleRepository.save(roleUser);
        }

        if (!hasAdminRole) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }
    }
}
