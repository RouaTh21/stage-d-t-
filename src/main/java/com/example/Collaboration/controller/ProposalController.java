package com.example.Collaboration.controller;

import com.example.Collaboration.dto.ProposalRequest;
import com.example.Collaboration.dto.VoteRequest;
import com.example.Collaboration.entity.Proposal;
import com.example.Collaboration.entity.VotingAnalytics;
import com.example.Collaboration.exception.ResourceNotFoundException;
import com.example.Collaboration.repository.OurUserRepo;
import com.example.Collaboration.repository.ProposalRepository;
import com.example.Collaboration.service.ProposalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private ProposalService proposalService;


    @PostMapping("/proposal_create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createProposal(@Valid @RequestBody ProposalRequest proposalRequest) {
        return proposalService.createProposal(proposalRequest);
    }

    @GetMapping("/{proposalId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getProposalById(@PathVariable("proposalId") Long id) {
        return proposalService.getProposalById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllPProposal(){
        return proposalService.getAllPProposal();
    }

    @PostMapping("/close-expired")
    public void closeExpiredProposals() {
        proposalService.closeExpiredProposals();
    }


}
