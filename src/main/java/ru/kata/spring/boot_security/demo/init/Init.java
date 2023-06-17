package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;

    @Autowired
    public Init(UserService userRepository) {
        this.userService = userRepository;
    }

    @PostConstruct
    private void postConstruct() {
        Role userRole = new Role(1L, "ROLE_USER");
        Role adminRole = new Role(2L, "ROLE_ADMIN");

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        User admin = new User("admin", "Admin", "admin@example.com", "admin_password");
        User normalUser = new User("user", "User", "user@example.com", "user_password");

        userService.addUser(admin, adminRoles);
        userService.addUser(normalUser, userRoles);
    }
}
