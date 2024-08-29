package com.example.Collaboration.controller;

import com.example.Collaboration.entity.Proposal;
import com.example.Collaboration.entity.VotingAnalytics;
import com.example.Collaboration.repository.ProposalRepository;
import com.example.Collaboration.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private ProposalRepository proposalRepository;
    @GetMapping("/proposal/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<VotingAnalytics> getAnalytics(@PathVariable("id") Long proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));
        VotingAnalytics analytics = analyticsService.generateAnalytics(proposal);
        return ResponseEntity.ok(analytics);
    }
}
