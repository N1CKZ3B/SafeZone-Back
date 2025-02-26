package eci.ieti.safezone.controller;

import eci.ieti.safezone.service.ApplicantService;
import eci.ieti.safezone.exception.ApplicantException;
import eci.ieti.safezone.exception.OfferException;
import eci.ieti.safezone.model.Applicant;
import eci.ieti.safezone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
        try {
            applicantService.save(applicant);
            return new ResponseEntity<>(applicant, HttpStatus.CREATED);
        } catch (ApplicantException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        return ResponseEntity.ok(applicantService.getApplicants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicantById(@PathVariable String id) {
        Optional<Applicant> applicant = applicantService.getApplicantById(id);
        return applicant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplicant(@PathVariable String id, @RequestBody Applicant updatedApplicant) {
        try {
            applicantService.update(id, updatedApplicant);
            return new ResponseEntity<>(updatedApplicant, HttpStatus.OK);
        } catch (ApplicantException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicant(@PathVariable String id) {
        applicantService.deleteOfferById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/choose")
    public ResponseEntity<?> chooseApplicant(@RequestBody Applicant applicant) {
        try {
            applicantService.choseApplicantForOffer(applicant);
            return ResponseEntity.ok("Applicant chosen successfully");
        } catch (ApplicantException | OfferException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/offer/{offerId}")
    public ResponseEntity<?> getApplicantsByOffer(@PathVariable String offerId) {
        try {
            List<Applicant> applicants = applicantService.getApplicantsByOfferId(offerId);
            return ResponseEntity.ok(applicants);
        } catch (OfferException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/chosen/{offerId}")
    public ResponseEntity<?> getChosenApplicant(@PathVariable String offerId) {
        try {
            Optional<User> user = applicantService.getChosenApplicantForOffer(offerId);
            return user.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (OfferException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

