package edu.bbte.idde.meim2276.backend.dao.interfaces;

import edu.bbte.idde.meim2276.backend.dao.datatypes.User;

import java.util.List;

public interface UserRepository {
    User findByUsername(String username);

    void updateTheme(String username, String theme);

    String getTheme(String username);

    void updateHighContrast(String username, String theme);

    String getHighContrast(String username);

    void updateLanguage(String username, String language);

    String getLanguage(String username);

    <S extends User> S save(S entity);

    Long getID(String username);

    void updateRole(Long id, Boolean role);

    void deleteUser(Long id);

    String getUsername(Long userId);

    Boolean isAdmin(String username);

    List<User> getAllUsers();

}
