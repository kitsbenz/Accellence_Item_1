package com.example.aps.service;

import com.example.aps.exception.BadRequestException;
import com.example.aps.model.Application;
import com.example.aps.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    public List<Application> getAll() {
        return repository.findAll();
    }

    public Application getById(Long id) {
        validateId(id);
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Application not found with id: " + id));
    }

    public Application save(Application app) {
        validateProductType(app.getProductType());
        app.setCreatedOn(LocalDateTime.now());
        app.setModifiedOn(LocalDateTime.now());
        return repository.save(app);
    }

    public Application update(Long id, Application updatedApp) {
        validateId(id);
        validateProductType(updatedApp.getProductType());

        Application app = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Application not found with id: " + id));

        app.setProductType(updatedApp.getProductType());
        app.setProductProgram(updatedApp.getProductProgram());
        app.setCardType(updatedApp.getCardType());
        app.setCampaignCode(updatedApp.getCampaignCode());
        app.setIsVip(updatedApp.getIsVip());
        app.setAppStatus(updatedApp.getAppStatus());
        app.setModifiedOn(LocalDateTime.now());

        return repository.save(app);
    }

    public void delete(Long id) {
        validateId(id);
        repository.deleteById(id);
    }

    // Validate ID not null
    private void validateId(Long id) {
        if (id == null) {
            throw new BadRequestException("Application ID (app_id) must not be null");
        }
    }

    // Validate productType not null or empty
    private void validateProductType(String productType) {
        if (productType == null || productType.trim().isEmpty()) {
            throw new BadRequestException("Product Type must not be null or empty");
        }
    }
}
