package com.example.Collaboration.dto;

import com.example.Collaboration.entity.Proposal;

public class ProposalDTO {
    private Long id;
    private String title;
    private Proposal.VotingType votingType;

    public ProposalDTO(Proposal proposal) {
        this.id = proposal.getId();
        this.title = proposal.getTitle();
        this.votingType = proposal.getVotingType();
    }
}
