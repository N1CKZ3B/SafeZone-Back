package eci.ieti.safezone.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import eci.ieti.safezone.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DynamoDBService {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<User> userTable;
    private final DynamoDbClient dynamoDbClient; // Agregar esta línea

    @Autowired
    public DynamoDBService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient; // Inicializar el campo
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.userTable = enhancedClient.table("users", TableSchema.fromBean(User.class));
    }

    public List<String> getCollections() {
        return dynamoDbClient.listTables().tableNames();
    }

    public List<User> findByPostuladoTrue() {
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(k -> k.partitionValue(AttributeValue.fromBool(true)));

        return userTable.query(queryConditional)
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    public void save(User user) {
        userTable.putItem(user);
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(userTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    public List<User> findByPostulado(boolean isPostulado) {
        System.out.println("Buscando usuarios con postulado: " + isPostulado);

        // Crear un mapa con el valor de la expresión, usando BOOL en lugar de S
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":postulado", AttributeValue.builder().bool(isPostulado).build());

        // Construir la expresión de filtro correctamente
        Expression expression = Expression.builder()
                .expression("postulado = :postulado")
                .expressionValues(expressionValues)
                .build();

        // Construir la solicitud de escaneo con la expresión de filtro
        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
                .filterExpression(expression)
                .build();

        // Realizar el escaneo
        List<User> results = userTable.scan(scanEnhancedRequest)
                .items()
                .stream()
                .collect(Collectors.toList());

        // Log de resultados obtenidos
        System.out.println("Número de usuarios encontrados: " + results.size());
        if (results.isEmpty()) {
            System.out.println("No se encontraron usuarios. Verifica que el atributo 'postulado' realmente existe y tiene el valor esperado.");
        } else {
            results.forEach(user -> System.out.println("Usuario encontrado: " + user));
        }

        return results;
    }
}
