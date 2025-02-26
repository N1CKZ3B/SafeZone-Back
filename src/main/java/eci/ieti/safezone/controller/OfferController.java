package eci.ieti.safezone.controller;

import eci.ieti.safezone.Service.OfferService;
import eci.ieti.safezone.exception.OfferException;
import eci.ieti.safezone.model.Offer;
import eci.ieti.safezone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/offers")
public class OfferController {
    private OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAll(){
        return new ResponseEntity<>(offerService.getOffers(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable String id){
        Optional<Offer> offer = offerService.getOfferById(id);
        return offer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Offer> saveOffer(@RequestBody Offer offer){
        offerService.save(offer);
        return new ResponseEntity<>(offer,HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOfferById(@PathVariable String id){
        offerService.deleteOfferById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Offer> updateOfferById(@RequestBody Offer offer){
        try{
            offerService.updateOffer(offer.getId(), offer);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        }catch (OfferException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
