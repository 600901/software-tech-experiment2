package no.xiv.software_tech_experiment2.service;

import no.xiv.software_tech_experiment2.domain.Poll;
import no.xiv.software_tech_experiment2.domain.User;
import no.xiv.software_tech_experiment2.domain.Vote;
import no.xiv.software_tech_experiment2.domain.VoteOption;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DomainManager {

    private final Map<String, User> users = new HashMap<>();
    private final Map<Long, Poll> polls = new HashMap<>();
    private final Map<Long, VoteOption> voteOptins = new HashMap<>();
    private final Map<Long, Vote> votes = new HashMap<>();

    private final Map<Long, String> pollToCreator = new HashMap<>();
    private final Map<Long, String> voteToUser = new HashMap<>();
    private final Map<Long, Long>  voteToPoll = new HashMap<>();
    private final Map<Long, Long> voteToOption = new HashMap<>();
    private final Map<Long, Long> optionToPoll = new HashMap<>();

    public User addUser(User user) {
        users.put(user.getUsername(), user);
        return user;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public Poll addPoll(String username, Poll poll) {
        polls.put(poll.getId(), poll);
        pollToCreator.put(poll.getId(), username);
        return poll;
    }

    public Poll getPoll(Long id) {
        return polls.get(id);
    }

    public VoteOption addVoteOption(Long pollId, VoteOption voteOption) {
        voteOptins.put(pollId, voteOption);
        optionToPoll.put(pollId, voteOption.getId());
        return voteOption;
    }

    public Long getVoteOption(Long voteId) {
        return voteToOption.get(voteId);
    }

    public Vote addVote(String userId, Long pollId, Vote vote) {
        votes.put(vote.getId(), vote);
        voteToUser.put(vote.getId(), userId);
        voteToOption.put(vote.getId(), vote.getVoteOptionId());
        voteToPoll.put(vote.getId(), pollId);
        return vote;
    }

    public String getVoter(Long voteId) {
        return voteToUser.get(voteId);
    }

    public Long getVotePoll(Long voteId) {
        return voteToPoll.get(voteId);
    }

}
