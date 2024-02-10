package com.yogesh.learningportal.controller;

import com.yogesh.learningportal.entity.Registration;
import com.yogesh.learningportal.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = registrationService.findAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        Optional<Registration> optionalRegistration = registrationService.findRegistrationById(id);
        return optionalRegistration.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> saveRegistration(@RequestBody Registration registration) {
        registrationService.saveRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration saved successfully");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeRegistration(@PathVariable Long id) {
        Optional<Registration> optionalRegistration = registrationService.findRegistrationById(id);
        if (optionalRegistration.isPresent()) {
            registrationService.removeRegistration(optionalRegistration.get());
            return ResponseEntity.ok("Registration removed successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
