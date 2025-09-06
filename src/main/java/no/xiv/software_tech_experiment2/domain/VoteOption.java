package no.xiv.software_tech_experiment2.domain;

public class VoteOption {

    private Long id;
    private Long pollId;
    private String caption;
    private int representationOrder;

    public VoteOption() {}

    public Long getId() {
        return id;
    }

    public Long getPollId() {
        return pollId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getRepresentationOrder() {
        return representationOrder;
    }

    public void setRepresentationOrder(int representationOrder) {
        this.representationOrder = representationOrder;
    }

    @Override
    public String toString() {
        return "VoteOption{" +
                "id=" + id +
                ", pollId=" + pollId +
                ", caption='" + caption + '\'' +
                ", representationOrder=" + representationOrder +
                '}';
    }

}
