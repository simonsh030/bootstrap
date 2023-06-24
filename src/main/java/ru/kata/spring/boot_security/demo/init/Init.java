package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(UserService userRepository, RoleService roleService) {
        this.userService = userRepository;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        roleService.addDefaultRoles();

        Role userRole = new Role(1L, "ROLE_USER");
        Role adminRole = new Role(2L, "ROLE_ADMIN");

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        User admin = new User("admin", "Admin", "admin@example.com", "admin_password");
        User normalUser = new User("user", "User", "user@example.com", "user_password");

        if (userService.isUserNameUnique(admin.getName())){
            userService.addUser(admin, adminRoles);
        }
        if (userService.isUserNameUnique(normalUser.getName())){
            userService.addUser(normalUser, userRoles);
        }

    }
}
