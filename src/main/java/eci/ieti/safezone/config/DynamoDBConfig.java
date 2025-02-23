package eci.ieti.safezone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.US_EAST_2) // Cambia la región si es necesario
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(${AWS_ACCESS_KEY_ID}, ${AWS_SECRET_ACCESS_KEY})
                ))
                .build();
    }
}
