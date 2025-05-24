package com.example.aps.controller;

import com.example.aps.exception.BadRequestException;
import com.example.aps.model.Application;
import com.example.aps.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getById(@PathVariable Long id) {
        Application app = service.getById(id);
        if (app == null) {
            throw new BadRequestException("Application not found with id: " + id);
        }
        return ResponseEntity.ok(app);
    }

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody Application app) {
        if (app.getProductType() == null || app.getProductType().isBlank()) {
            throw new BadRequestException("Product type is required.");
        }
        return ResponseEntity.ok(service.save(app));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> update(@PathVariable Long id, @RequestBody Application app) {
        if (app.getProductType() == null || app.getProductType().isBlank()) {
            throw new BadRequestException("Product type is required.");
        }
        Application updated = service.update(id, app);
        if (updated == null) {
            throw new BadRequestException("Application not found with id: " + id);
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
