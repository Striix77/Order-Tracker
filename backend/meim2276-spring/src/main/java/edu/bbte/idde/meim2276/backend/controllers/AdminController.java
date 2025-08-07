package edu.bbte.idde.meim2276.backend.controllers;

import edu.bbte.idde.meim2276.backend.business.services.CustomUserDetailsService;
import edu.bbte.idde.meim2276.backend.dao.datatypes.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final transient Logger logger = org.slf4j.LoggerFactory.getLogger(AdminController.class);
    private final transient CustomUserDetailsService userDetailsService;

    @Autowired
    public AdminController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        logger.info("Getting all users!");
        return userDetailsService.getAllUsers();
    }

    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with id: {}", id);
        userDetailsService.deleteUser(id);
    }

    @PutMapping("/make-admin/{id}")
    public void makeAdmin(@PathVariable Long id) {
        logger.info("Making user with id: {} an admin", id);
        userDetailsService.updateRole(id, true);
    }
}