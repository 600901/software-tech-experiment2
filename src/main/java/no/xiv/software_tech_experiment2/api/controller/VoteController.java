package no.xiv.software_tech_experiment2.api.controller;

import no.xiv.software_tech_experiment2.domain.Vote;
import no.xiv.software_tech_experiment2.service.DomainManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls/{pollId}/votes")
public class VoteController {

    private final DomainManager domainManager;

    public VoteController(DomainManager domainManager) {
        this.domainManager = domainManager;
    }

    @PostMapping
    public ResponseEntity<Vote> castVote(@PathVariable Long pollId, @RequestBody Vote vote) {
        var created = domainManager.addVote(vote.getVoterUsername(), pollId, vote);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{voteId}")
    public Vote changeVote(@PathVariable Long pollId,
                           @PathVariable Long voteId,
                           @RequestBody Vote vote) {
        return domainManager.updateVote(voteId, vote.getVoteOptionId());
    }

    @GetMapping
    public List<Vote> listVotes(@PathVariable Long pollId) {
        return domainManager.listVotesForPoll(pollId);
    }
}

