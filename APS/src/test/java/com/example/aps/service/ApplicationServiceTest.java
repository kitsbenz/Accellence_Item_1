package com.example.aps.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.aps.exception.BadRequestException;
import com.example.aps.model.Application;
import com.example.aps.repository.ApplicationRepository;

class ApplicationServiceTest {

    private ApplicationRepository repository;
    private ApplicationService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ApplicationRepository.class);
        service = new ApplicationService(repository);
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(new Application(), new Application()));
        List<Application> apps = service.getAll();
        assertEquals(2, apps.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById_Success() {
        Application app = new Application();
        app.setAppId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(app));
        Application result = service.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getAppId());
    }

    @Test
    void testGetById_NullId_Throws() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.getById(null));
        assertEquals("Application ID (app_id) must not be null", ex.getMessage());
    }

    @Test
    void testGetById_NotFound_Throws() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.getById(99L));
        assertEquals("Application not found with id: 99", ex.getMessage());
    }

    @Test
    void testSave_Success() {
        Application app = new Application();
        app.setProductType("TypeA");

        when(repository.save(any(Application.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Application saved = service.save(app);

        assertNotNull(saved.getCreatedOn());
        assertNotNull(saved.getModifiedOn());
        assertEquals("TypeA", saved.getProductType());
        verify(repository, times(1)).save(app);
    }

    @Test
    void testSave_ProductTypeNull_Throws() {
        Application app = new Application();
        app.setProductType(null);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.save(app));
        assertEquals("Product Type must not be null or empty", ex.getMessage());
    }

    @Test
    void testUpdate_Success() {
        Application existingApp = new Application();
        existingApp.setAppId(1L);
        existingApp.setProductType("OldType");

        Application updatedApp = new Application();
        updatedApp.setProductType("NewType");
        updatedApp.setProductProgram("ProgramX");
        updatedApp.setCardType("CardY");
        updatedApp.setCampaignCode("CampaignZ");
        updatedApp.setIsVip(true);
        updatedApp.setAppStatus("Active");

        when(repository.findById(1L)).thenReturn(Optional.of(existingApp));
        when(repository.save(any(Application.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Application result = service.update(1L, updatedApp);

        assertEquals("NewType", result.getProductType());
        assertEquals("ProgramX", result.getProductProgram());
        assertEquals("CardY", result.getCardType());
        assertEquals("CampaignZ", result.getCampaignCode());
        assertTrue(result.getIsVip());
        assertEquals("Active", result.getAppStatus());
        assertNotNull(result.getModifiedOn());
        verify(repository, times(1)).save(existingApp);
    }

    @Test
    void testUpdate_NullId_Throws() {
        Application updatedApp = new Application();
        updatedApp.setProductType("NewType");

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.update(null, updatedApp));
        assertEquals("Application ID (app_id) must not be null", ex.getMessage());
    }

    @Test
    void testUpdate_ProductTypeNull_Throws() {
        Application updatedApp = new Application();
        updatedApp.setProductType(null);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.update(1L, updatedApp));
        assertEquals("Product Type must not be null or empty", ex.getMessage());
    }

    @Test
    void testUpdate_NotFound_Throws() {
        Application updatedApp = new Application();
        updatedApp.setProductType("NewType");

        when(repository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.update(99L, updatedApp));
        assertEquals("Application not found with id: 99", ex.getMessage());
    }

    @Test
    void testDelete_Success() {
        doNothing().when(repository).deleteById(1L);
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NullId_Throws() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.delete(null));
        assertEquals("Application ID (app_id) must not be null", ex.getMessage());
    }
}
