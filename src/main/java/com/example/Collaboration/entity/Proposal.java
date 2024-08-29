package com.example.Collaboration.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Proposals")
public class Proposal  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    private String title;

    @NotBlank
    @Size(max = 140)
    private String question;


    @OneToMany(
            mappedBy = "proposal",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Choice> choices = new ArrayList<>();

    @NotNull
    private Instant expirationDateTime;

    @Enumerated(EnumType.STRING)
    private VotingType votingType;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    @ElementCollection
    private List<Long> winningChoices = new ArrayList<>();


    @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroupMember> userGroupMembers = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public VotingType getVotingType() {
        return votingType;
    }

    public List<Long> getWinningChoices() {
        return winningChoices;
    }

    public void setWinningChoices(List<Long> winningChoices) {
        this.winningChoices = winningChoices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public void setVotingType(VotingType votingType) {
        this.votingType = votingType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<UserGroupMember> getUserGroupMembers() {
        return userGroupMembers;
    }

    public void setUserGroupMembers(List<UserGroupMember> userGroupMembers) {
        this.userGroupMembers = userGroupMembers;
    }

    public void addChoice(Choice choice) {
      choices.add(choice);
      choice.setProposal(this);
    }


    public enum VotingType {
        PUBLIC,GROUP
    }
    public enum Status {
        OPEN,
        CLOSED
    }
}
