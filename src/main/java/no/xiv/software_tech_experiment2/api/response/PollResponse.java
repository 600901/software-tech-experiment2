package no.xiv.software_tech_experiment2.api.response;

import java.util.List;

public record PollResponse(

        Long id,
        String question,
        String publishedAt,
        String validUntil,
        List<VoteOptionResponse> options

) { }
