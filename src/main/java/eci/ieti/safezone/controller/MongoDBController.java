package eci.ieti.safezone.controller;

import eci.ieti.safezone.Service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/mongo")
public class MongoDBController {
    @Autowired
    private MongoDBService mongoDBService;

    @GetMapping("/collections")
    public Set<String> getCollections() {
        return mongoDBService.getCollections();
    }
}