package com.example.demo.controller;

import com.example.demo.Entity.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    // Retrieve all admins
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Retrieve an admin by email
    @GetMapping("/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email) {
        return adminRepository.findByEmail(email)
                .map(admin -> ResponseEntity.ok().body(admin))
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new admin
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Return 409 if email already exists
        }

        Admin savedAdmin = adminRepository.save(admin); // Save the new admin
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin); // Return 201 Created with saved admin
    }

    // Update an admin by email
    @PutMapping("/{email}")
    public ResponseEntity<Admin> updateAdminByEmail(@PathVariable String email, @RequestBody Admin adminDetails) {
        return adminRepository.findByEmail(email).map(existingAdmin -> {
            existingAdmin.setPassword(adminDetails.getPassword());
            existingAdmin.setPhoneNo(adminDetails.getPhoneNo());
            existingAdmin.setAddress(adminDetails.getAddress());
            Admin updatedAdmin = adminRepository.save(existingAdmin); // Save using the repository
            return ResponseEntity.ok(updatedAdmin);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete an admin by email
    @DeleteMapping("/{email}")
    public ResponseEntity<?> removeAdmin(@PathVariable String email) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email); // Fetch admin by email
        if (adminOptional.isPresent()) {
            adminRepository.delete(adminOptional.get()); // Use repository to delete
            return ResponseEntity.ok("Admin deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
    }
}
