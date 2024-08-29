package com.example.Collaboration.service;

import com.example.Collaboration.entity.Choice;
import com.example.Collaboration.entity.Proposal;
import com.example.Collaboration.entity.VotingAnalytics;
import com.example.Collaboration.repository.UserGroupMemberRepository;
import com.example.Collaboration.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserGroupMemberRepository userGroupMemberRepository;

    public VotingAnalytics generateAnalytics(Proposal proposal) {
        VotingAnalytics analytics = new VotingAnalytics();

        // Calculate total votes and participation rate
        int totalVotes = voteRepository.countByProposalId(proposal.getId());
        int totalVoters = voteRepository.countDistinctVotersByProposalId(proposal.getId());
        double participationRate = (totalVoters > 0) ? (double) totalVotes / totalVoters * 100 : 0;
        analytics.setParticipationRate(participationRate);
        // Analyze vote distribution
        Map<String, Integer> voteDistribution = new HashMap<>();
        for (Choice choice : proposal.getChoices()) {
            int choiceVotes = voteRepository.countByChoiceId(choice.getId());
            voteDistribution.put(choice.getText(), choiceVotes);
        }
        analytics.setVoteDistribution(voteDistribution);
        // Analyze the influence of user scores
        int totalScore = 0;
        Map<String, Integer> scoreInfluence = new HashMap<>();
        Choice winningChoice = null;
        int maxScore = 0;
        for (Choice choice : proposal.getChoices()) {
            int choiceScore = voteRepository.calculateTotalScoreForChoice(choice.getId());
            scoreInfluence.put(choice.getText(), choiceScore);
            totalScore += choiceScore;

            if (choiceScore > maxScore) {
                maxScore = choiceScore;
                winningChoice = choice;
            }
        }
        analytics.setScoreInfluence(scoreInfluence);
        // Analyze decision outcome
        if (winningChoice != null) {
            analytics.setWinningChoice(winningChoice.getText());
            analytics.setWinningScore(maxScore);
        }
        return analytics;
    }
}
