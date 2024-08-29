package com.example.Collaboration.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "votes",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "proposal_id",
                "user_id"
        })
})
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "proposal_id",nullable = false)
    private Proposal proposal;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "choice_id",nullable = false)
    private Choice choice;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private OurUsers user;

    @Column(name = "user_score")
    private Integer userScore;

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public OurUsers getUser() {
        return user;
    }

    public void setUser(OurUsers user) {
        this.user = user;
    }
}
