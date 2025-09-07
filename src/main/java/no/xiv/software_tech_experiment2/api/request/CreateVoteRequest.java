package no.xiv.software_tech_experiment2.api.request;

public record CreateVoteRequest(

        String voterUsername,
        Long voteOptionId

) { }
