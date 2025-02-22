package eci.ieti.safezone;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://admin:Ieti%402025%40@ieti.a1ypo.mongodb.net/?retryWrites=true&w=majority&appName=Ieti"; // Cambia esto si usas un servidor remoto

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("miBaseDeDatos");
            System.out.println("Conexión exitosa a la base de datos: " + database.getName());
        } catch (Exception e) {
            System.out.println("Error conectando a MongoDB: " + e.getMessage());
        }
    }
}