package edu.bbte.idde.meim2276.backend.controllers;

import edu.bbte.idde.meim2276.backend.config.auth.JwtAuth;
import edu.bbte.idde.meim2276.backend.dao.datatypes.User;
import edu.bbte.idde.meim2276.backend.dao.interfaces.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SignupController {

    private final transient UserRepository userRepository;
    private final transient UserDetailsService userDetailsService;
    private final transient PasswordEncoder passwordEncoder;
    private final transient JwtAuth jwtAuth;

    @Autowired
    public SignupController(UserRepository userRepository, UserDetailsService userDetailsService,
                            PasswordEncoder passwordEncoder, JwtAuth jwtAuth) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuth = jwtAuth;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsAdmin(false);
        userRepository.save(user);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtAuth.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(jwt);
    }
}