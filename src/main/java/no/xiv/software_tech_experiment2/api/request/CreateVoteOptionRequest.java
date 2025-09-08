package no.xiv.software_tech_experiment2.api.request;

public record CreateVoteOptionRequest(

        String caption,
        Integer representationOrder

) { }
