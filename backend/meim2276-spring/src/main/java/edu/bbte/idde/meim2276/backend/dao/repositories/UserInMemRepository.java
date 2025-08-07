package edu.bbte.idde.meim2276.backend.dao.repositories;


import edu.bbte.idde.meim2276.backend.dao.datatypes.User;
import edu.bbte.idde.meim2276.backend.dao.interfaces.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("InMem")
public class UserInMemRepository implements UserRepository {
    private final transient AtomicLong idCounter = new AtomicLong();
    private final transient Map<Long, User> users;

    public UserInMemRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public User findByUsername(String username) {
        List<User> usersList = List.copyOf(users.values());
        for (User user : usersList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void updateTheme(String username, String theme) {
        users.values()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().ifPresent(userToUpdate -> userToUpdate.setTheme(theme));

    }

    @Override
    public String getTheme(String username) {
        return users.values()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().map(User::getTheme).orElse("");
    }

    @Override
    public void updateHighContrast(String username, String theme) {
        users.values()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().ifPresent(userToUpdate -> userToUpdate.setHighContrast(theme));
    }

    @Override
    public String getHighContrast(String username) {
        return users.values()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().map(User::getHighContrast).orElse("");
    }

    @Override
    public void updateLanguage(String username, String language) {
        users.values()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().ifPresent(userToUpdate -> userToUpdate.setLanguage(language));
    }

    @Override
    public void updateRole(Long id, Boolean role) {
        users.values()
                .stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst().ifPresent(userToUpdate -> userToUpdate.setIsAdmin(role));
    }

    @Override
    public void deleteUser(Long id) {
        users.values()
                .removeIf(user -> Objects.equals(id, user.getId()));
    }

    @Override
    public String getUsername(Long userId) {
        return users.values()
                .stream()
                .filter(user -> Objects.equals(userId, user.getId()))
                .findFirst().map(User::getUsername).orElse("");
    }

    @Override
    public Boolean isAdmin(String username) {
        return users.values()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().map(User::getIsAdmin).orElse(false);
    }

    @Override
    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }

    @Override
    public String getLanguage(String username) {
        return users.values()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().map(User::getLanguage).orElse("");
    }

    @Override
    public <S extends User> S save(S entity) {
        entity.setId(idCounter.incrementAndGet());
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Long getID(String username) {
        return users.values()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().map(User::getId).orElse(null);
    }
}
