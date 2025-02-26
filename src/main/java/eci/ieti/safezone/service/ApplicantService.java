package eci.ieti.safezone.service;

import eci.ieti.safezone.exception.ApplicantException;
import eci.ieti.safezone.exception.OfferException;
import eci.ieti.safezone.model.Applicant;
import eci.ieti.safezone.model.Offer;
import eci.ieti.safezone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicantService {
    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<Applicant> applicantTable;
    private final DynamoDbClient dynamoDbClient;
    private UserService userService;
    private OfferService offerService;

    @Autowired
    public ApplicantService(DynamoDbClient dynamoDbClient, UserService userService, OfferService offerService) {
        this.dynamoDbClient = dynamoDbClient; // Inicializar el campo
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.applicantTable = enhancedClient.table("applicants", TableSchema.fromBean(Applicant.class));
        this.userService = userService;
        this.offerService = offerService;
    }
    public void save(Applicant applicant) throws ApplicantException {
        Optional<User> user = userService.findById(applicant.getUserId());
        Optional<Offer> offer = offerService.getOfferById(applicant.getOfferId());

        if (user.isPresent() && offer.isPresent()) {
            if (!offer.get().isClosed()){
                applicantTable.putItem(applicant);
            }
            throw new ApplicantException(ApplicantException.CLOSED_OFFER);
        } else {
            throw new ApplicantException(ApplicantException.INVALID);
        }
    }

    public List<Applicant> getApplicants(){
        List<Applicant> applicants = new ArrayList<>();

        applicantTable.scan(ScanEnhancedRequest.builder().build())
                .items()
                .forEach(applicants::add);

        return applicants;
    }
    public Optional<Applicant> getApplicantById(String id){
        return Optional.ofNullable(applicantTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }
    public void deleteOfferById(String id) {
        applicantTable.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
    public void update(String id, Applicant updatedApplicant) throws ApplicantException {
        Optional<Applicant> existingApplicant = getApplicantById(id);

        if (existingApplicant.isPresent()) {
            Applicant applicant = existingApplicant.get();

            // Actualizar solo los campos modificables
            if (updatedApplicant.getOfferId() != null) {
                applicant.setOfferId(updatedApplicant.getOfferId());
            }
            if (updatedApplicant.getUserId() != null) {
                applicant.setUserId(updatedApplicant.getUserId());
            }
            applicant.setChosen(updatedApplicant.isChosen()); // Asumiendo que siempre se puede actualizar

            applicantTable.putItem(applicant);
        } else {
            throw new ApplicantException(ApplicantException.NOT_FOUND);
        }
    }

    public void choseApplicantForOffer(Applicant applicant) throws ApplicantException, OfferException {
        Optional<Applicant> existingApplicant = getApplicantById(applicant.getId());
        if (existingApplicant.isPresent()) {
            Applicant selectedApplicant = existingApplicant.get();
            Optional<Offer> offer = offerService.getOfferById(selectedApplicant.getOfferId());
            if (offer.isPresent()) {
                Offer existingOffer = offer.get();
                if (!existingOffer.isClosed()) {
                    selectedApplicant.setChosen(true);
                    applicantTable.putItem(selectedApplicant);
                    offerService.closeOffer(existingOffer.getId());
                } else {
                    throw new ApplicantException(ApplicantException.CLOSED_OFFER);
                }
            } else {
                throw new OfferException(OfferException.NOT_FOUND);
            }
        } else {
            throw new ApplicantException(ApplicantException.NOT_FOUND);
        }
    }
    public List<Applicant> getApplicantsByOfferId(String offerId) throws OfferException {
        Optional<Offer> offer = offerService.getOfferById(offerId);
        if (offer.isEmpty()) {
            throw new OfferException(OfferException.NOT_FOUND);
        }

        List<Applicant> applicants = new ArrayList<>();

        applicantTable.scan(ScanEnhancedRequest.builder().build())
                .items()
                .stream()
                .filter(applicant -> applicant.getOfferId().equals(offerId))
                .forEach(applicants::add);

        return applicants;
    }
    public Optional<User> getChosenApplicantForOffer(String offerId) throws OfferException {
        Optional<Offer> offer = offerService.getOfferById(offerId);
        if (offer.isEmpty()) {
            throw new OfferException(OfferException.NOT_FOUND);
        }

        return applicantTable.scan(ScanEnhancedRequest.builder().build())
                .items()
                .stream()
                .filter(Applicant::isChosen) // Filtra solo los aplicantes elegidos
                .filter(applicant -> applicant.getOfferId().equals(offerId))
                .map(applicant -> userService.findById(applicant.getUserId())) // Obtiene el usuario
                .filter(Optional::isPresent) // Filtra los Optional vacíos
                .map(Optional::get) // Obtiene el usuario real
                .findFirst(); // Retorna el primer usuario encontrado
    }

}
