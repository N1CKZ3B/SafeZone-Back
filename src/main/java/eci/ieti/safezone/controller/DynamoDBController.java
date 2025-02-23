package eci.ieti.safezone.controller;

import eci.ieti.safezone.Service.DynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamodb")
public class DynamoDBController {
    @Autowired
    private DynamoDBService dynamoDBService;

    @GetMapping("/tables")
    public List<String> getTables() {
        return dynamoDBService.getCollections();
    }
}
