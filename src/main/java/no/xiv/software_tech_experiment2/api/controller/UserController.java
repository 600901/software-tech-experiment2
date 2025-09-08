package no.xiv.software_tech_experiment2.api.controller;

import no.xiv.software_tech_experiment2.domain.User;
import no.xiv.software_tech_experiment2.service.DomainManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final DomainManager domainManager;

    public UserController(DomainManager domainManager) {
        this.domainManager = domainManager;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        var created = domainManager.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<User> listUsers() {
        return domainManager.listUsers();
    }
}

