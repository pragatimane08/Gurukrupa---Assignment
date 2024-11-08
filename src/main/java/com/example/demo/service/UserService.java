package com.example.demo.service;

import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        // Set createdAt and updatedAt timestamps
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public Optional<User> updateUser(String email, User userDetails) {
        return userRepository.findByEmail(email).map(user -> {
            user.setPassword(userDetails.getPassword());
            user.setPhoneNo(userDetails.getPhoneNo());
            user.setAddress(userDetails.getAddress());
            user.setRole(userDetails.getRole());
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Update timestamp
            return userRepository.save(user);
        });
    }


    public boolean deleteUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }
}


//package com.example.demo.service;
//
//import com.example.demo.Entity.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    // Create a new user with validation and password encryption
//    public User registerUser(User user) {
//        if (!isValidPassword(user.getPassword())) {
//            throw new IllegalArgumentException("Password must include at least 1 digit, 1 uppercase letter, 1 lowercase letter, and 1 special character.");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user);
//
//        if (user.getRole() == User.Role.CUSTOMER) {
//            String sql = "INSERT INTO customer (EMAIL, PASSWORD, phone_no, ADDRESS) VALUES (?, ?, ?, ?)";
//            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getPhoneNo(), user.getAddress());
//        } else if (user.getRole() == User.Role.ADMIN) {
//            String sql = "INSERT INTO admin (EMAIL, PASSWORD, phone_no, ADDRESS) VALUES (?, ?, ?, ?)";
//            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getPhoneNo(), user.getAddress());
//        }
//
//        return savedUser;
//    }
//
//    // Validate password strength
//    private boolean isValidPassword(String password) {
//        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$");
//    }
//
//    // Retrieve all users
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    // Retrieve a user by email
//    public Optional<User> getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    // Update an existing user
//    public Optional<User> updateUser(String email, User userDetails) {
//        return userRepository.findByEmail(email).map(user -> {
//            user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // Encrypt the password
//            user.setPhoneNo(userDetails.getPhoneNo());
//            user.setAddress(userDetails.getAddress());
//            user.setRole(userDetails.getRole());
//            return userRepository.save(user);
//        });
//    }
//
//    // Delete a user by email
//    public boolean deleteUser(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user.isPresent()) {
//            userRepository.delete(user.get());
//            return true;
//        }
//        return false;
//    }
//}
