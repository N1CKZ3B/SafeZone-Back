package eci.ieti.safezone.Service;

import eci.ieti.safezone.exception.OfferException;
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
public class OfferService {
    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<Offer> offerTable;
    private final DynamoDbClient dynamoDbClient;
    private UserService userService;

    @Autowired
    public OfferService(DynamoDbClient dynamoDbClient, UserService userService) {
        this.dynamoDbClient = dynamoDbClient; // Inicializar el campo
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.offerTable = enhancedClient.table("offers", TableSchema.fromBean(Offer.class));
        this.userService = userService;
    }
    public void save(Offer offer){
        userService.findById(offer.getUserId()).ifPresent(user -> {
            offer.setClosed(false);
            offerTable.putItem(offer);
        });
    }
    public List<Offer> getOffers(){
        List<Offer> offers = new ArrayList<>();

        offerTable.scan(ScanEnhancedRequest.builder().build())
                .items()
                .forEach(offers::add);

        return offers;
    }
    public Optional<Offer> getOfferById(String id){
        return Optional.ofNullable(offerTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }
    public void deleteOfferById(String id) {
        offerTable.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public Offer updateOffer(String id, Offer newOffer) throws OfferException {
        return getOfferById(id).map(existingOffer -> {
            existingOffer.setTitle(newOffer.getTitle());
            existingOffer.setDescription(newOffer.getDescription());
            existingOffer.setPrice(newOffer.getPrice());
            existingOffer.setNumOfPeople(newOffer.getNumOfPeople());
            existingOffer.setClosed(newOffer.isClosed());
            offerTable.putItem(existingOffer);
            return existingOffer;
        }).orElseThrow(() -> new OfferException(OfferException.NOT_FOUND));
    }
    public void closeOffer(String id) throws OfferException {
        Optional<Offer> optionalOffer = getOfferById(id);
        if (optionalOffer.isPresent()) {
            Offer existingOffer = optionalOffer.get();
            existingOffer.setClosed(true);
            offerTable.putItem(existingOffer);
        } else {
            throw new OfferException(OfferException.NOT_FOUND);
        }
    }
}
