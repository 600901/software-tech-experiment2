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

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoterUsername() {
        return voterUsername;
    }

    public Long getVoteOptionId() {
        return voteOptionId;
    }

    public void setVoteOptionId(Long voteOptionId) {
        this.voteOptionId = voteOptionId;
    }

    public Instant getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(Instant votedAt) {
        this.votedAt = votedAt;
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
