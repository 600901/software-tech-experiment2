package no.xiv.software_tech_experiment2.api.request;

public record CreateUserRequest(

        String username,
        String email,
        String password

) { }
