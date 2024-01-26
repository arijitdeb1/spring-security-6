package com.arijit.springsecurity.controller;

import com.arijit.springsecurity.entity.Employee;
import com.arijit.springsecurity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Employee employee) {
        ResponseEntity<String> response = null;
        try {
            employee.setPwd(passwordEncoder.encode(employee.getPwd()));
            Employee savedEmployee = employeeRepository.save(employee);
            if (employee.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED).body("User " + employee.getEmail() + " has been successfully registered");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while registering user. Please try again later");
        }
        return response;
    }
}
