package com.example.Collaboration.entity;

import java.util.Map;

public class VotingAnalytics {

    private double participationRate;
    private Map<String, Integer> voteDistribution;
    private Map<String, Integer> scoreInfluence;

    private String winningChoice;
    private int winningScore;


    public double getParticipationRate() {
        return participationRate;
    }

    public void setParticipationRate(double participationRate) {
        this.participationRate = participationRate;
    }

    public Map<String, Integer> getVoteDistribution() {
        return voteDistribution;
    }

    public void setVoteDistribution(Map<String, Integer> voteDistribution) {
        this.voteDistribution = voteDistribution;
    }

    public Map<String, Integer> getScoreInfluence() {
        return scoreInfluence;
    }

    public void setScoreInfluence(Map<String, Integer> scoreInfluence) {
        this.scoreInfluence = scoreInfluence;
    }

    public String getWinningChoice() {
        return winningChoice;
    }

    public void setWinningChoice(String winningChoice) {
        this.winningChoice = winningChoice;
    }

    public int getWinningScore() {
        return winningScore;
    }

    public void setWinningScore(int winningScore) {
        this.winningScore = winningScore;
    }
}
