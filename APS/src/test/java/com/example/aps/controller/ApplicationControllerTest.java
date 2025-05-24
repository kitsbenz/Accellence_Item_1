package com.example.aps.controller;

import com.example.aps.model.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Application createSampleApplication() {
        Application app = new Application();
        app.setProductType("Loan");
        app.setProductProgram("ProgramA");
        app.setCardType("Visa");
        app.setCampaignCode("CAMP123");
        app.setIsVip(true);
        app.setAppStatus("NEW");
        return app;
    }

    @Test
    public void testCreateAndGetApplication() {
        Application newApp = createSampleApplication();

        // Create
        ResponseEntity<Application> createResponse = restTemplate.postForEntity("/api/applications", newApp, Application.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        Application createdApp = createResponse.getBody();
        assertNotNull(createdApp);
        assertNotNull(createdApp.getAppId());

        // Get by ID
        ResponseEntity<Application> getResponse = restTemplate.getForEntity("/api/applications/" + createdApp.getAppId(), Application.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Application retrievedApp = getResponse.getBody();
        assertNotNull(retrievedApp);
        assertEquals("Loan", retrievedApp.getProductType());
    }

    @Test
    public void testCreateApplicationMissingProductType() {
        Application app = new Application();
        app.setProductProgram("ProgramA");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/applications", app, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Product type is required"));
    }

    @Test
    public void testUpdateApplication() {
        Application newApp = createSampleApplication();

        ResponseEntity<Application> createResponse = restTemplate.postForEntity("/api/applications", newApp, Application.class);
        Application createdApp = createResponse.getBody();
        assertNotNull(createdApp);

        createdApp.setProductType("UpdatedLoan");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Application> entity = new HttpEntity<>(createdApp, headers);

        ResponseEntity<Application> updateResponse = restTemplate.exchange(
                "/api/applications/" + createdApp.getAppId(),
                HttpMethod.PUT,
                entity,
                Application.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Application updatedApp = updateResponse.getBody();
        assertNotNull(updatedApp);
        assertEquals("UpdatedLoan", updatedApp.getProductType());
    }

    @Test
    public void testUpdateApplicationNotFound() {
        Application app = createSampleApplication();
        app.setProductType("UpdatedLoan");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Application> entity = new HttpEntity<>(app, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/applications/999999",
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Application not found with id"));
    }

    @Test
    public void testGetAllApplications() {
        ResponseEntity<List<Application>> response = restTemplate.exchange(
                "/api/applications",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Application>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Application> apps = response.getBody();
        assertNotNull(apps);
        assertTrue(apps.size() >= 0);
    }

    @Test
    public void testDeleteApplication() {
        Application newApp = createSampleApplication();

        ResponseEntity<Application> createResponse = restTemplate.postForEntity("/api/applications", newApp, Application.class);
        Application createdApp = createResponse.getBody();
        assertNotNull(createdApp);
        Long id = createdApp.getAppId();

        // Delete
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/applications/" + id, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // Confirm deletion: should get 400 BadRequest with your setup
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/applications/" + id, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, getResponse.getStatusCode());
        assertTrue(getResponse.getBody().contains("Application not found with id"));
    }
}
