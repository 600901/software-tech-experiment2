package no.xiv.software_tech_experiment2.domain;

import java.time.Instant;

public class Vote {

    private Long id;
    private String voterUsername;
    private Long voteOptionId;
    private Instant votedAt;

    public Vote() {}

    public Long getId() {
        return id;
    }

    public String getVoterUsername() {
        return voterUsername;
    }

    public Long getVoteOptionId() {
        return voteOptionId;
    }

    public Instant getVotedAt() {
        return votedAt;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voterUsername='" + voterUsername + '\'' +
                ", voteOptionId=" + voteOptionId +
                ", votedAt=" + votedAt +
                '}';
    }

}
