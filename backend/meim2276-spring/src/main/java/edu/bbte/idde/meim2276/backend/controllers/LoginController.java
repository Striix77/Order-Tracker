package edu.bbte.idde.meim2276.backend.controllers;

import edu.bbte.idde.meim2276.backend.config.auth.JwtAuth;
import edu.bbte.idde.meim2276.backend.dao.datatypes.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final transient AuthenticationManager authenticationManager;
    private final transient UserDetailsService userDetailsService;
    private final transient JwtAuth jwtAuth;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService, JwtAuth jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtAuth = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (AuthenticationException e) {
            log.error("Incorrect username or password", e);
            throw new SecurityException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtAuth.generateToken(userDetails.getUsername());

        
        return ResponseEntity.ok(jwt);
    }

}