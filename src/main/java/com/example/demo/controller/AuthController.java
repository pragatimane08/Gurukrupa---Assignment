//package com.example.demo.controller;
//
//import com.example.demo.Entity.User;
//import com.example.demo.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthService authService;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(
//            @RequestParam String email,
//            @RequestParam String password,
//            @RequestParam(required = false) String phoneNo,
//            @RequestParam(required = false) String address,
//            @RequestParam(defaultValue = "CUSTOMER") User.Role role) {  // Updated Role type
//        return ResponseEntity.ok(authService.registerUser(email, password, phoneNo, address, role));  // Removed extra User.Role
//    }
//
//    // No specific login endpoint needed; Spring Security handles this automatically
//}
