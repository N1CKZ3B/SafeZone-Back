package eci.ieti.safezone.service;

import eci.ieti.safezone.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import eci.ieti.safezone.model.User;

import java.util.List;


@Service
public class DynamoDBService {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<User> userTable;
    private final DynamoDbTable<Offer> offerTable;
    private final DynamoDbClient dynamoDbClient; // Agregar esta línea

    @Autowired
    public DynamoDBService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient; // Inicializar el campo
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.userTable = enhancedClient.table("users", TableSchema.fromBean(User.class));
        this.offerTable = enhancedClient.table("offers", TableSchema.fromBean(Offer.class));
    }

    public List<String> getCollections() {
        return dynamoDbClient.listTables().tableNames();
    }

}
