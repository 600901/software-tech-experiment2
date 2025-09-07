package no.xiv.software_tech_experiment2.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Poll {

    private Long id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private List<VoteOption> options = new ArrayList<>();

    public Poll() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public List<VoteOption> getOptions() {
        return options;
    }

    public void setOptions(List<VoteOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", publishedAt=" + publishedAt +
                ", validUntil=" + validUntil +
                ", options=" + options +
                '}';
    }

}
