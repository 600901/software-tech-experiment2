package no.xiv.software_tech_experiment2.api.controller;

import no.xiv.software_tech_experiment2.domain.Poll;
import no.xiv.software_tech_experiment2.domain.VoteOption;
import no.xiv.software_tech_experiment2.service.DomainManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls")
public class PollController {

    private final DomainManager domainManager;

    public PollController(DomainManager domainManager) {
        this.domainManager = domainManager;
    }

    @PostMapping
    public Poll createPoll(@RequestParam String username, @RequestBody Poll poll) {
        Poll createdPoll = domainManager.addPoll(username, poll);
        // Add options
        for (VoteOption option : poll.getOptions()) {
            domainManager.addVoteOption(createdPoll.getId(), option);
        }
        return createdPoll;
    }

    @GetMapping
    public List<Poll> listPolls() {
        return domainManager.listPolls();
    }

    @DeleteMapping("/{pollId}")
    public void deletePoll(@PathVariable Long pollId) {
        domainManager.deletePoll(pollId);
    }

    @GetMapping("/{pollId}/options")
    public List<VoteOption> getPollOptions(@PathVariable Long pollId) {
        return domainManager.getPollOptions(pollId);
    }
}

