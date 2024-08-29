package com.example.Collaboration.service;

import com.example.Collaboration.dto.ProposalRequest;
import com.example.Collaboration.entity.*;
import com.example.Collaboration.exception.ResourceNotFoundException;
import com.example.Collaboration.repository.ProposalRepository;
import com.example.Collaboration.repository.UserGroupMemberRepository;
import com.example.Collaboration.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private EmailService emailService;


    public ResponseEntity<?> createProposal(ProposalRequest proposalRequest) {
        Proposal proposal = new Proposal();
        proposal.setTitle(proposalRequest.getTitle());
        proposal.setQuestion((proposalRequest.getQuestion()));
        proposalRequest.getChoices().forEach(choiceRequest -> {
            proposal.addChoice(new Choice(choiceRequest.getText()));
        });
        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(proposalRequest.getProposalLength().getDays()))
                .plus(Duration.ofHours(proposalRequest.getProposalLength().getHours()));

        proposal.setExpirationDateTime(expirationDateTime);
        proposal.setVotingType(proposalRequest.getVotingType());

        Proposal savedProposal = proposalRepository.save(proposal);
        return new ResponseEntity<>(savedProposal, HttpStatus.CREATED);

    }

    public ResponseEntity<?> getProposalById(Long id) {
        Optional<Proposal> proposalOptional = proposalRepository.findById(id);
        if (proposalOptional.isPresent()) {
            Proposal proposal = proposalOptional.get();
            return new ResponseEntity<>(proposal, HttpStatus.OK);
        }   else {
            return new ResponseEntity<>("Proposal not found", HttpStatus.NOT_FOUND);

        }

    }
    public ResponseEntity<?> getAllPProposal() {
            List<Proposal> proposals = proposalRepository.findAll();
            return ResponseEntity.ok(proposals);
        }

    @Transactional
    @Scheduled(cron = "0 * * * * *") // Every minute
    public void closeExpiredProposals() {

        List<Proposal> expiredProposals = proposalRepository.findByStatusAndExpirationDateTimeBefore(Proposal.Status.OPEN, Instant.now());
        for (Proposal proposal : expiredProposals) {
            closeProposalAndSendResults(proposal);
        }
    }
    private void closeProposalAndSendResults(Proposal proposal) {

        List<Choice> choices = proposal.getChoices();
        Choice winningChoice = null;
        int maxScore = 0;

        for (Choice choice : choices) {
            int totalScore = voteRepository.calculateTotalScoreForChoice(choice.getId());

            if (totalScore > maxScore) {
                        maxScore = totalScore;
                        winningChoice = choice;
            }
        }
        if (winningChoice != null) {
            proposal.getWinningChoices().clear(); // Ensure we only have one winning choice
            proposal.getWinningChoices().add(winningChoice.getId());
        }
        proposal.setStatus(Proposal.Status.CLOSED);
        proposalRepository.save(proposal);

        sendResultsToParticipants(proposal, winningChoice, maxScore);
    }
    private void sendResultsToParticipants(Proposal proposal, Choice winningChoice, int winningScore) {
        List<Vote> votes = voteRepository.findByProposalId(proposal.getId());

        for (Vote vote : votes) {
            OurUsers user = vote.getUser();
            String subject = "Résultats de la proposition: " + proposal.getTitle();
            String message = String.format(
                    "Bonjour %s,\n\nLes résultats de la proposition \"%s\" sont maintenant disponibles.\n" +
                            "Choix gagnant: %s avec un score de %d.\n\nMerci de votre participation.",
                    user.getUsername(), proposal.getTitle(), winningChoice.getText(), winningScore);
            emailService.sendEmail(user.getEmail(), subject, message);
        }
    }

}







