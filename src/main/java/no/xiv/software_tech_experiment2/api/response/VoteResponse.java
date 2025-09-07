package no.xiv.software_tech_experiment2.api.response;

public record VoteResponse(

        Long id,
        String voterUsername,
        Long voteOptionId,
        String votedAt

) { }
