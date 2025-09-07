package no.xiv.software_tech_experiment2.service;

import no.xiv.software_tech_experiment2.domain.Poll;
import no.xiv.software_tech_experiment2.domain.User;
import no.xiv.software_tech_experiment2.domain.Vote;
import no.xiv.software_tech_experiment2.domain.VoteOption;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DomainManager {

    private final Map<String, User> users = new HashMap<>();
    private final Map<Long, Poll> polls = new HashMap<>();
    private final Map<Long, VoteOption> voteOptions = new HashMap<>();
    private final Map<Long, Vote> votes = new HashMap<>();

    private final Map<Long, String> pollToCreator = new HashMap<>();
    private final Map<Long, String> voteToUser = new HashMap<>();
    private final Map<Long, Long>  voteToPoll = new HashMap<>();
    private final Map<Long, Long> voteToOption = new HashMap<>();
    private final Map<Long, Long> optionToPoll = new HashMap<>();
    private final Map<Long, List<Long>> pollToOptions = new HashMap<>();

    private Long pollIdCounter = 1L;
    private Long voteOptionIdCounter = 1L;
    private Long voteIdCounter = 1L;

    public User addUser(User user) {
        users.put(user.getUsername(), user);
        return user;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    public Poll addPoll(String username, Poll poll) {
        poll.setId(pollIdCounter++);
        polls.put(poll.getId(), poll);
        pollToCreator.put(poll.getId(), username);
        pollToOptions.put(poll.getId(), new ArrayList<>());
        return poll;
    }

    public Poll getPoll(Long id) {
        return polls.get(id);
    }

    public List<Poll> listPolls() {
        return new ArrayList<>(polls.values());
    }

//    public void deletePoll(Long pollId) {
//        polls.remove(pollId);
//        List<Long> options = pollToOptions.remove(pollId);
//        if (options != null) {
//            for (Long optionId : options) voteOptions.remove(optionId);
//        }
//    }

    public void deletePoll(Long pollId) {
        // Remove the poll itself
        polls.remove(pollId);

        // Remove all vote options
        List<Long> options = pollToOptions.remove(pollId);
        if (options != null) {
            for (Long optionId : options) {
                voteOptions.remove(optionId);
                optionToPoll.remove(optionId);
            }
        }

        // Remove all votes for this poll
        List<Long> votesToRemove = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : voteToPoll.entrySet()) {
            if (entry.getValue().equals(pollId)) {
                votesToRemove.add(entry.getKey());
            }
        }
        for (Long voteId : votesToRemove) {
            votes.remove(voteId);
            voteToPoll.remove(voteId);
            voteToOption.remove(voteId);
            voteToUser.remove(voteId);
        }

        // Remove creator mapping
        pollToCreator.remove(pollId);
    }

    public VoteOption addVoteOption(Long pollId, VoteOption voteOption) {
        voteOption.setId(voteOptionIdCounter++);
        voteOptions.put(voteOption.getId(), voteOption);
        optionToPoll.put(voteOption.getId(), pollId);
        pollToOptions.get(pollId).add(voteOption.getId());
        return voteOption;
    }

    public Long getVoteOption(Long voteId) {
        return voteToOption.get(voteId);
    }

    public List<VoteOption> getPollOptions(Long pollId) {
        List<Long> ids = pollToOptions.getOrDefault(pollId, List.of());
        List<VoteOption> options = new ArrayList<>();
        for (Long id : ids) options.add(voteOptions.get(id));
        return options;
    }

    public Vote addVote(String username, Long pollId, Vote vote) {
        vote.setId(voteIdCounter++);
        vote.setVotedAt(java.time.Instant.now());
        votes.put(vote.getId(), vote);
        voteToUser.put(vote.getId(), username);
        voteToOption.put(vote.getId(), vote.getVoteOptionId());
        voteToPoll.put(vote.getId(), pollId);
        return vote;
    }

    public Vote updateVote(Long voteId, Long newOptionId) {
        Vote vote = votes.get(voteId);
        if (vote != null) {
            vote.setVoteOptionId(newOptionId);
            vote.setVotedAt(java.time.Instant.now());
            voteToOption.put(voteId, newOptionId);
        }
        return vote;
    }

    public List<Vote> listVotesForPoll(Long pollId) {
        List<Vote> result = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : voteToPoll.entrySet()) {
            if (entry.getValue().equals(pollId)) {
                result.add(votes.get(entry.getKey()));
            }
        }
        return result;
    }

    public String getVoter(Long voteId) {
        return voteToUser.get(voteId);
    }

    public Long getVotePoll(Long voteId) {
        return voteToPoll.get(voteId);
    }

}
