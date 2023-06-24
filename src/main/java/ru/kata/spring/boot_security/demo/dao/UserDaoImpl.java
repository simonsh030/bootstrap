package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user, Set<Role> roles) {
            user.setRoles(roles);
            entityManager.persist(user);

    }
    @Override
    public void deleteUserById(long id) {
        entityManager.remove(findUserById(id));
    }

    @Override
    public User findUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User updateUser(User user, Set<Role> roles) {
            user.setRoles(roles);
            return entityManager.merge(user);
    }


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select distinct u from User u join fetch u.roles", User.class).getResultList();
    }

    @Override
    public User findUserByName(String name) {
        return entityManager.createQuery("select u from User u join fetch u.roles where u.name=:name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public boolean isUserNameUnique(String name) {
        Query query = entityManager.createQuery("select count(u) from User u where u.name = :name");
        query.setParameter("name", name);
        long count = (long) query.getSingleResult();
        return count == 0;
    }

}