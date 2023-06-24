package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    void addUser(User user, Set<Role> roles);
    void deleteUserById(long id);

    User updateUser(User user, Set<Role> roles);

    List<User> getAllUsers();

    User findUserById(long id);

    User findUserByName(String name);


    boolean isUserNameUnique(String name);
}