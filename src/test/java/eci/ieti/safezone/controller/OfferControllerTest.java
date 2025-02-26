package eci.ieti.safezone.controller;

import eci.ieti.safezone.controller.OfferController;
import eci.ieti.safezone.exception.OfferException;
import eci.ieti.safezone.model.Offer;
import eci.ieti.safezone.service.OfferService;
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
class OfferControllerTest {

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferController offerController;

    private Offer offer1;
    private Offer offer2;

    @BeforeEach
    void setUp() {
        offer1 = new Offer();
        offer1.setId("1");
        offer1.setClosed(false);
        offer1.setUserId("user123");
        offer1.setTitle("Discounted Meal");
        offer1.setDescription("50% off on all meals");
        offer1.setPrice("10.99");
        offer1.setNumOfPeople(2);

        offer2 = new Offer();
        offer2.setId("2");
        offer2.setClosed(true);
        offer2.setUserId("user456");
        offer2.setTitle("Free Coffee");
        offer2.setDescription("Buy one get one free");
        offer2.setPrice("5.00");
        offer2.setNumOfPeople(1);
    }

    @Test
    void getAll_ShouldReturnListOfOffers() {
        List<Offer> offers = Arrays.asList(offer1, offer2);
        when(offerService.getOffers()).thenReturn(offers);

        ResponseEntity<List<Offer>> response = offerController.getAll();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(offerService, times(1)).getOffers();
    }

    @Test
    void getOfferById_ShouldReturnOffer_WhenOfferExists() {
        when(offerService.getOfferById("1")).thenReturn(Optional.of(offer1));

        ResponseEntity<Offer> response = offerController.getOfferById("1");

        assertEquals(OK, response.getStatusCode());
        assertEquals(offer1, response.getBody());
        verify(offerService, times(1)).getOfferById("1");
    }

    @Test
    void getOfferById_ShouldReturnNotFound_WhenOfferDoesNotExist() {
        when(offerService.getOfferById("3")).thenReturn(Optional.empty());

        ResponseEntity<Offer> response = offerController.getOfferById("3");

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(offerService, times(1)).getOfferById("3");
    }

    @Test
    void saveOffer_ShouldReturnCreatedStatus() {

        ResponseEntity<Offer> response = offerController.saveOffer(offer1);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals(offer1, response.getBody());
        verify(offerService, times(1)).save(offer1);
    }

    @Test
    void deleteOfferById_ShouldReturnNoContentStatus() {
        doNothing().when(offerService).deleteOfferById("1");

        ResponseEntity<?> response = offerController.deleteOfferById("1");

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(offerService, times(1)).deleteOfferById("1");
    }


}
