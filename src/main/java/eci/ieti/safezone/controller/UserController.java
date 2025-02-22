package eci.ieti.safezone.controller;

import eci.ieti.safezone.Repository.UserRepository;
import eci.ieti.safezone.User;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET: Obtener todos los usuarios postulados (postulado == true)
    @GetMapping("/postulados")
    public List<User> getPostulados() {
        return userRepository.findByPostuladoTrue();
    }

    // POST: Marcar a un usuario como elegido
    @PostMapping("/{id}/elegir")
    public User setElegido(@PathVariable String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setElegido(true);
            return userRepository.save(user);
        }
        return null; // O lanzar una excepción si prefieres
    }
}