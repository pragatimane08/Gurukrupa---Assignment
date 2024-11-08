package com.example.demo.controller;

import com.example.demo.Entity.User;
import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.service.UserService;
import com.example.demo.Validator.PasswordValidator;
import jakarta.validation.Valid;  // Updated import for Jakarta EE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;  // Ensure this is present for validation

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRegistrationDTO userDto) {
        // Validate the password
        if (!PasswordValidator.isValidPassword(userDto.getPassword())) {
            return ResponseEntity.badRequest()
                    .body("Password must include at least 1 uppercase letter, " +
                            "1 lowercase letter, 1 digit, and 1 special character.");
        }

        // Convert DTO to User entity
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // Ensure the password is hashed in the setter
        user.setPhoneNo(userDto.getPhoneNo());
        user.setAddress(userDto.getAddress());
        user.setRole(User.Role.valueOf(userDto.getRole().toUpperCase()));

        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully: " + savedUser.getEmail());
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUserByEmail(@PathVariable String email, @RequestBody User userDetails) {
        Optional<User> updatedUser = userService.updateUser(email, userDetails);
        return updatedUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> removeUser(@PathVariable String email) {
        boolean deleted = userService.deleteUser(email);
        return deleted ? ResponseEntity.ok("User deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}


//package com.example.demo.controller;
//
//import com.example.demo.Entity.User;
//import com.example.demo.repository.UserRepository;
//import jakarta.persistence.Table;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/user")
//
//public class UserController {
//    @Autowired
//    private UserRepository users ;
//
//    @GetMapping()
//    public List<User> getAllStudents(){
//        return users.findAll();
//    }
//
//    @GetMapping("/{email}")
//    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
//        return users.findByEmail(email)
//                .map(user -> ResponseEntity.ok().body(user))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
////    @PostMapping
////    public User createUser(@RequestBody User user){
////        return users.save(user);
////    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        // Save user to the USER table
//        User savedUser = users.save(user);
//
//        // Check role and move data to the corresponding table without removing from USER
//        if ("CUSTOMER".equals(user.getRole())) {
//            // Copy data to customer table
//            String sql = "INSERT INTO customer (EMAIL, PASSWORD, phone_no, ADDRESS) VALUES (?, ?, ?, ?)";
//            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getPhoneNo(), user.getAddress());
//        } else if ("ADMIN".equals(user.getRole())) {
//            // Copy data to admin table
//            String sql = "INSERT INTO admin (EMAIL, PASSWORD, phone_no, ADDRESS) VALUES (?, ?, ?, ?)";
//            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getPhoneNo(), user.getAddress());
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
//    }
//
//
//
//    @PutMapping("/{email}")
//    public ResponseEntity<User> updateUserByEmail(@PathVariable String email, @RequestBody User userDetails) {
//        return users.findByEmail(email).map(user -> {
//            user.setPassword(userDetails.getPassword());
//            user.setPhoneNo(userDetails.getPhoneNo());
//            user.setAddress(userDetails.getAddress());
//            user.setRole(userDetails.getRole());
//            User updatedUser = users.save(user);
//            return ResponseEntity.ok(updatedUser);
//        }).orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{email}")
//    public ResponseEntity<?> removeUser(@PathVariable String email) {
//        Optional<User> user = users.findByEmail(email);
//        if (user.isPresent()) {
//            users.delete(user.get());
//            return ResponseEntity.ok("User deleted successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//    }
//
//
//    }
