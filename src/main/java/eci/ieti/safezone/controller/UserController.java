package eci.ieti.safezone.controller;

import eci.ieti.safezone.Service.DynamoDBService;
import eci.ieti.safezone.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final DynamoDBService dynamoDBService;

    public UserController(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    // GET: Obtener todos los usuarios postulados (postulado == true)
    @GetMapping("/postulados")
    public List<User> getPostulados() {
        return dynamoDBService.findByPostuladoTrue();
    }

    // POST: Marcar a un usuario como elegido
    @PostMapping("/{id}/elegir")
    public User setElegido(@PathVariable String id) {
        Optional<User> optionalUser = dynamoDBService.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setElegido(true);
            dynamoDBService.save(user);
            return user;
        }
        return null; // O lanzar una excepción si prefieres
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        dynamoDBService.save(user);
        return user;
    }
}
