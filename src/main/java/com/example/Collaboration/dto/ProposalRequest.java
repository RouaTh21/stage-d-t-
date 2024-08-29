package com.example.Collaboration.dto;

import com.example.Collaboration.entity.Proposal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProposalRequest {

    @Size(max = 20)
    private String title;

    @NotBlank
    @Size(max = 140)
    private String question;

    @NotNull
    @Size(min = 2, max = 6)
    @Valid
    private List<ChoiceRequest> choices ;


    @NotNull
    @Valid
    private ProposalLength proposalLength;

    @NotNull
    private Proposal.VotingType votingType;

    public Proposal.VotingType getVotingType() {
        return votingType;
    }

    public void setVotingType(Proposal.VotingType votingType) {
        this.votingType = votingType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceRequest> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceRequest> choices) {
        this.choices = choices;
    }

    public ProposalLength getProposalLength() {
        return proposalLength;
    }

    public void setProposalLength(ProposalLength proposalLength) {
        this.proposalLength = proposalLength;
    }
}
