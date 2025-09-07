package no.xiv.software_tech_experiment2.api.controller;

import no.xiv.software_tech_experiment2.domain.User;
import no.xiv.software_tech_experiment2.service.DomainManager;
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
    public User createUser(@RequestBody User user) {
        return domainManager.addUser(user);
    }

    @GetMapping
    public List<User> listUsers() {
        return domainManager.listUsers();
    }
}

