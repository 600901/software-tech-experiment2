package no.xiv.software_tech_experiment2.api.response;

public record VoteOptionResponse(

        Long id,
        Long pollId,
        String caption,
        int representationOrder

) { }
