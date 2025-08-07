package edu.bbte.idde.meim2276.backend.business.services;

import edu.bbte.idde.meim2276.backend.dao.datatypes.User;
import edu.bbte.idde.meim2276.backend.dao.interfaces.UserRepository;
import edu.bbte.idde.meim2276.backend.dao.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final transient UserRepository userRepository;
    private final transient OrderRepository orderRepository;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        log.info("User found: {}", username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getIsAdmin() ? "ADMIN" : "USER")
                .build();
    }

    @Transactional
    public void updateTheme(String username, String theme) {
        userRepository.updateTheme(username, theme);
    }

    public String getTheme(String username) {
        return userRepository.getTheme(username);
    }

    @Transactional
    public void updateHighContrast(String username, String theme) {
        userRepository.updateHighContrast(username, theme);
    }

    public String getHighContrast(String username) {
        return userRepository.getHighContrast(username);
    }

    @Transactional
    public void updateLanguage(String username, String language) {
        userRepository.updateLanguage(username, language);
    }

    @Transactional
    public void updateRole(Long id, boolean role) {
        userRepository.updateRole(id, role);
    }

    @Transactional
    public void deleteUser(Long id) {
        orderRepository.deleteOrdersByUserId(id);
        userRepository.deleteUser(id);
    }

    public String getLanguage(String username) {
        return userRepository.getLanguage(username);
    }

    public Boolean isAdmin(String username) {
        return userRepository.isAdmin(username);
    }

    public List<User> getAllUsers() {

        return userRepository.getAllUsers();
    }

    public Long getID(String username) {
        return userRepository.getID(username);
    }
}
