package com.example.demo.controller;

import com.example.demo.Entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000") // Adjust the URL if your React app runs on a different port

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository; // Renamed to avoid conflict

    @GetMapping()
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(); // Fetch all customers from the repository
    }

    @GetMapping("/{email}")
    public ResponseEntity<Customer> getUserByEmail(@PathVariable String email) {
        return customerRepository.findByEmail(email) // Fetch by email
                .map(customer -> ResponseEntity.ok().body(customer))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Return 409 if email already exists
        }

        Customer savedCustomer = customerRepository.save(customer); // Save the new customer
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer); // Return 201 Created with saved customer
    }


    @PutMapping("/{email}")
    public ResponseEntity<Customer> updateCustomerByEmail(@PathVariable String email, @RequestBody Customer customerDetails) {
        return customerRepository.findByEmail(email).map(existingCustomer -> {
            existingCustomer.setPassword(customerDetails.getPassword());
            existingCustomer.setPhoneNo(customerDetails.getPhoneNo());
            existingCustomer.setAddress(customerDetails.getAddress());
            Customer updatedCustomer = customerRepository.save(existingCustomer); // Save using the repository
            return ResponseEntity.ok(updatedCustomer);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> removeUser(@PathVariable String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email); // Fetch customer by email
        if (customerOptional.isPresent()) {
            customerRepository.delete(customerOptional.get()); // Use repository to delete
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
