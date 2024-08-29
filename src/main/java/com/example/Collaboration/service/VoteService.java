package com.example.Collaboration.service;

import com.example.Collaboration.dto.VoteRequest;
import com.example.Collaboration.entity.*;
import com.example.Collaboration.exception.ResourceNotFoundException;
import com.example.Collaboration.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private UserGroupMemberRepository userGroupMemberRepository;


    public ResponseEntity<?> castVote(Long proposalId, VoteRequest voteRequest, Integer userId) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Proposal", "id", proposalId));

        if (proposal.getExpirationDateTime().isBefore(Instant.now())) {
            return new ResponseEntity<>("Sorry! This proposal has already expired", HttpStatus.BAD_REQUEST);
        }

        OurUsers user = ourUserRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Choice selectedChoice = proposal.getChoices().stream()
                .filter(choice -> choice.getId().equals(voteRequest.getChoiceId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Choice", "id", voteRequest.getChoiceId()));

        //for public voting
        Vote vote = new Vote();
        vote.setChoice(selectedChoice);
        vote.setProposal(proposal);
        vote.setUser(user);

        //for group voting
        if (proposal.getVotingType() == Proposal.VotingType.GROUP) {
            List<UserGroupMember> userGroupMembers = userGroupMemberRepository.findByProposalIdAndUserId(proposalId, userId);
            if (userGroupMembers.isEmpty()) {
                return new ResponseEntity<>("User is not allowed to vote",HttpStatus.FORBIDDEN);
            }
            UserGroupMember userGroupMember = userGroupMembers.get(0);

            int voteValue = userGroupMember.getScore();
            vote.setUserScore(voteValue);

        } else {
            vote.setUserScore(1);
        }
        try {
            voteRepository.save(vote);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>("Sorry! You have already cast your vote in this proposal", HttpStatus.BAD_REQUEST);
        }
        List<ChoiceVoteCount> votes = voteRepository.countByProposalIdGroupByChoiceId(proposalId);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));

        return new ResponseEntity<>(Map.of(
                "message", "Vote cast successfully",
                "voteCounts", choiceVotesMap
        ), HttpStatus.OK);        }
    }


