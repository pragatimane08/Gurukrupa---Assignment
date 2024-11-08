//package com.example.demo.service;
//
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import com.example.demo.Entity.User;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public String registerUser(String email, String password, String phoneNo, String address, User.Role role) {
//        if (userRepository.findByEmail(email).isPresent()) {
//            throw new RuntimeException("Email is already registered");
//        }
//
//        validatePassword(password);
//
//        User user = new User();
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setPhoneNo(phoneNo);
//        user.setAddress(address);
//        user.setRole(role);
//        userRepository.save(user);
//
//        return "User registered successfully";
//    }
//
//    private void validatePassword(String password) {
//        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
//            throw new RuntimeException("Password must contain at least 1 digit, 1 uppercase, 1 lowercase letter, " +
//                    "1 special symbol, and be at least 8 characters long");
//        }
//    }
//}
