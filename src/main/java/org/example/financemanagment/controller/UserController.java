package org.example.financemanagment.controller;

import org.example.financemanagment.domain.entity.LoginRequest;
import org.example.financemanagment.domain.entity.User;
import org.example.financemanagment.service.UserService;
import org.example.financemanagment.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        User registeredUser = userService.register(user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful.");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        boolean isAuthenticated = userService.login(username, password);
        if (isAuthenticated) {
            Utils.userId = userService.getUserId(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok("Giriş uğurludur.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("İstifadəçi məlumatları yanlışdır.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User successfully deleted.");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
