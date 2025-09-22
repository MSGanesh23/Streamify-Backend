package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = loginService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", user.getEmail());
        response.put("role", user.getRole());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (loginRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(0);
        loginRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/AddAdmin")
    public ResponseEntity<?> addAdmin(@RequestBody User user) {
        if (loginRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(1);
        loginRepo.save(user);
        return ResponseEntity.ok("Admin added successfully");
    }

    @GetMapping("/users/search")
    public ResponseEntity<?> getUsersByEmail(@RequestParam(required = false) String email) {
        List<User> users;
        if (email != null && !email.trim().isEmpty()) {
            users = loginRepo.findByEmailStartingWithIgnoreCase(email.trim());
        } else {
            users = loginRepo.findAll();
        }
        return ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "*") // Allow all origins (for testing)
    @DeleteMapping("/users/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        Optional<User> user = loginRepo.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        loginRepo.delete(user.get());
        return ResponseEntity.ok("User deleted successfully");
    }
    
    @PutMapping("/user/{id}/subscribe")
    public ResponseEntity<?> updateSubscription(@PathVariable Long id) {
        Optional<User> userOptional = loginRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSubscribed(true); // assumes User entity has get/setSubscribed
            loginRepo.save(user);
            return ResponseEntity.ok("Subscription updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    


}
