package eci.ieti.safezone.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class MongoDBService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Set<String> getCollections() {
        return mongoTemplate.getCollectionNames();
    }
}
