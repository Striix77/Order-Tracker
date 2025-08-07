package edu.bbte.idde.meim2276.backend.dao.repositories;

import edu.bbte.idde.meim2276.backend.dao.datatypes.User;
import edu.bbte.idde.meim2276.backend.dao.interfaces.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("DB")
public interface UserDbRepository extends JpaRepository<User, Long>, UserRepository {
    @Override
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.theme = :theme WHERE u.username = :username")
    @Override
    void updateTheme(@Param("username") String username, @Param("theme") String theme);

    @Query("SELECT u.theme FROM User u WHERE u.username = :username")
    @Override
    String getTheme(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.highContrast = :highContrast WHERE u.username = :username")
    @Override
    void updateHighContrast(@Param("username") String username, @Param("highContrast") String theme);

    @Query("SELECT u.highContrast FROM User u WHERE u.username = :username")
    @Override
    String getHighContrast(@Param("username") String username);


    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.language = :language WHERE u.username = :username")
    @Override
    void updateLanguage(@Param("username") String username, @Param("language") String language);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isAdmin = :isAdmin WHERE u.id = :id")
    @Override
    void updateRole(@Param("id") Long id, @Param("isAdmin") Boolean isAdmin);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    @Override
    void deleteUser(@Param("id") Long id);

    @Query("SELECT u.language FROM User u WHERE u.username = :username")
    @Override
    String getLanguage(@Param("username") String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    @Override
    Long getID(@Param("username") String username);

    @Query("SELECT u.username FROM User u WHERE u.id = :id")
    @Override
    String getUsername(@Param("id") Long id);

    @Query("SELECT u.isAdmin FROM User u WHERE u.username = :username")
    @Override
    Boolean isAdmin(@Param("username") String username);

    @Query("SELECT u FROM User u")
    @Override
    List<User> getAllUsers();
}
