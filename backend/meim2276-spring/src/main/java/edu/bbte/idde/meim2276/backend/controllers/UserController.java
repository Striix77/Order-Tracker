package edu.bbte.idde.meim2276.backend.controllers;

import edu.bbte.idde.meim2276.backend.business.services.CustomUserDetailsService;
import edu.bbte.idde.meim2276.backend.controllers.dto.LanguageUpdateDTO;
import edu.bbte.idde.meim2276.backend.controllers.dto.ThemeUpdateDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    private final transient Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    private final transient CustomUserDetailsService userDetailsService;

    @Autowired
    public UserController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/settheme")
    public void setTheme(@RequestBody ThemeUpdateDTO themeUpdateDTO) {
        logger.info("Setting theme for user with username: {} to: {}",
                themeUpdateDTO.getUsername(), themeUpdateDTO.getTheme());
        userDetailsService.updateTheme(themeUpdateDTO.getUsername(), themeUpdateDTO.getTheme());
    }

    @GetMapping("/gettheme/{username}")
    public String getTheme(@PathVariable String username) {
        logger.info("Getting theme for user with username: {}", username);
        return userDetailsService.getTheme(username);
    }

    @PostMapping("/sethighcontrast")
    public void setHighContrast(@RequestBody ThemeUpdateDTO themeUpdateDTO) {
        logger.info("Setting high contrast theme for user with username: {} to: {}",
                themeUpdateDTO.getUsername(), themeUpdateDTO.getTheme());
        userDetailsService.updateHighContrast(themeUpdateDTO.getUsername(), themeUpdateDTO.getTheme());
    }

    @GetMapping("/gethighcontrast/{username}")
    public String getHighContrast(@PathVariable String username) {
        logger.info("Getting high contrast theme for user with username: {}", username);
        return userDetailsService.getHighContrast(username);
    }

    @PostMapping("/setlanguage")
    public void setLanguage(@RequestBody LanguageUpdateDTO languageUpdateDTO) {
        logger.info("Setting language for user with username: {} to: {}",
                languageUpdateDTO.getUsername(), languageUpdateDTO.getLanguage());
        userDetailsService.updateLanguage(languageUpdateDTO.getUsername(), languageUpdateDTO.getLanguage());
    }

    @GetMapping("/getlanguage/{username}")
    public String getLanguage(@PathVariable String username) {
        logger.info("Getting language for user with username: {}", username);
        String language = userDetailsService.getLanguage(username);
        logger.info("Language for user with username: {} is: {}", username, language);
        return language;
    }

    @GetMapping("/get-id/{username}")
    public Long getID(@PathVariable String username) {
        logger.info("Getting ID for user with username: {}", username);
        return userDetailsService.getID(username);
    }

    @GetMapping("/is-admin/{username}")
    public Boolean isAdmin(@PathVariable String username) {
        logger.info("Getting admin info for user with username: {}", username);
        return userDetailsService.isAdmin(username);
    }


}