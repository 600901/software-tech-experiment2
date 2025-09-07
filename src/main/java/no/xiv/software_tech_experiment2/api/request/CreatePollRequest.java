package no.xiv.software_tech_experiment2.api.request;

import java.util.List;

public record CreatePollRequest(

        String question,
        String publishedAt,
        String validUntil,
        List<CreateVoteOptionRequest> options

) { }
