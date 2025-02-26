package eci.ieti.safezone.controller;

import eci.ieti.safezone.controller.ApplicantController;
import eci.ieti.safezone.exception.ApplicantException;
import eci.ieti.safezone.exception.OfferException;
import eci.ieti.safezone.model.Applicant;
import eci.ieti.safezone.model.User;
import eci.ieti.safezone.service.ApplicantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class ApplicantControllerTest {

    @Mock
    private ApplicantService applicantService;

    @InjectMocks
    private ApplicantController applicantController;

    private Applicant applicant1;
    private Applicant applicant2;

    @BeforeEach
    void setUp() {
        applicant1 = new Applicant();
        applicant1.setId("1");
        applicant1.setOfferId("offer123");
        applicant1.setUserId("user123");
        applicant1.setChosen(false);

        applicant2 = new Applicant();
        applicant2.setId("2");
        applicant2.setOfferId("offer456");
        applicant2.setUserId("user456");
        applicant2.setChosen(true);
    }

    @Test
    void createApplicant_ShouldReturnCreatedStatus() throws ApplicantException {
        doNothing().when(applicantService).save(applicant1);

        ResponseEntity<Applicant> response = applicantController.createApplicant(applicant1);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals(applicant1, response.getBody());
        verify(applicantService, times(1)).save(applicant1);
    }

    @Test
    void createApplicant_ShouldReturnBadRequest_WhenExceptionOccurs() throws ApplicantException {
        doThrow(new ApplicantException("Error saving applicant"))
                .when(applicantService).save(applicant1);

        ResponseEntity<Applicant> response = applicantController.createApplicant(applicant1);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        verify(applicantService, times(1)).save(applicant1);
    }

    @Test
    void getAllApplicants_ShouldReturnListOfApplicants() {
        List<Applicant> applicants = Arrays.asList(applicant1, applicant2);
        when(applicantService.getApplicants()).thenReturn(applicants);

        ResponseEntity<List<Applicant>> response = applicantController.getAllApplicants();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(applicantService, times(1)).getApplicants();
    }

    @Test
    void getApplicantById_ShouldReturnApplicant_WhenExists() {
        when(applicantService.getApplicantById("1")).thenReturn(Optional.of(applicant1));

        ResponseEntity<?> response = applicantController.getApplicantById("1");

        assertEquals(OK, response.getStatusCode());
        assertEquals(applicant1, response.getBody());
        verify(applicantService, times(1)).getApplicantById("1");
    }

    @Test
    void getApplicantById_ShouldReturnNotFound_WhenDoesNotExist() {
        when(applicantService.getApplicantById("3")).thenReturn(Optional.empty());

        ResponseEntity<?> response = applicantController.getApplicantById("3");

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(applicantService, times(1)).getApplicantById("3");
    }

    @Test
    void updateApplicant_ShouldReturnOk_WhenUpdatedSuccessfully() throws ApplicantException {
        doNothing().when(applicantService).update("1", applicant1);

        ResponseEntity<?> response = applicantController.updateApplicant("1", applicant1);

        assertEquals(OK, response.getStatusCode());
        assertEquals(applicant1, response.getBody());
        verify(applicantService, times(1)).update("1", applicant1);
    }

    @Test
    void updateApplicant_ShouldReturnBadRequest_WhenExceptionOccurs() throws ApplicantException {
        doThrow(new ApplicantException("Update failed"))
                .when(applicantService).update("1", applicant1);

        ResponseEntity<?> response = applicantController.updateApplicant("1", applicant1);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        verify(applicantService, times(1)).update("1", applicant1);
    }

    @Test
    void deleteApplicant_ShouldReturnNoContent() {
        doNothing().when(applicantService).deleteOfferById("1");

        ResponseEntity<String> response = applicantController.deleteApplicant("1");

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(applicantService, times(1)).deleteOfferById("1");
    }

    @Test
    void chooseApplicant_ShouldReturnOk_WhenSuccessful() throws ApplicantException, OfferException {
        doNothing().when(applicantService).choseApplicantForOffer(applicant1);

        ResponseEntity<?> response = applicantController.chooseApplicant(applicant1);

        assertEquals(OK, response.getStatusCode());
        verify(applicantService, times(1)).choseApplicantForOffer(applicant1);
    }

    @Test
    void chooseApplicant_ShouldReturnBadRequest_WhenExceptionOccurs() throws ApplicantException, OfferException {
        doThrow(new ApplicantException("Error choosing applicant"))
                .when(applicantService).choseApplicantForOffer(applicant1);

        ResponseEntity<?> response = applicantController.chooseApplicant(applicant1);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        verify(applicantService, times(1)).choseApplicantForOffer(applicant1);
    }

    @Test
    void getApplicantsByOffer_ShouldReturnList_WhenSuccessful() throws OfferException {
        List<Applicant> applicants = Arrays.asList(applicant1, applicant2);
        when(applicantService.getApplicantsByOfferId("offer123")).thenReturn(applicants);

        ResponseEntity<?> response = applicantController.getApplicantsByOffer("offer123");

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, ((List<?>) response.getBody()).size());
        verify(applicantService, times(1)).getApplicantsByOfferId("offer123");
    }

    @Test
    void getChosenApplicant_ShouldReturnUser_WhenSuccessful() throws OfferException {
        User user = new User();
        when(applicantService.getChosenApplicantForOffer("offer123")).thenReturn(Optional.of(user));

        ResponseEntity<?> response = applicantController.getChosenApplicant("offer123");

        assertEquals(OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(applicantService, times(1)).getChosenApplicantForOffer("offer123");
    }

    @Test
    void getChosenApplicant_ShouldReturnNotFound_WhenNoUserExists() throws OfferException {
        when(applicantService.getChosenApplicantForOffer("offer123")).thenReturn(Optional.empty());

        ResponseEntity<?> response = applicantController.getChosenApplicant("offer123");

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(applicantService, times(1)).getChosenApplicantForOffer("offer123");
    }
}

