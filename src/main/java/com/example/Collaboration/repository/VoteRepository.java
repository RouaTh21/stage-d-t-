package com.example.Collaboration.repository;

import com.example.Collaboration.entity.ChoiceVoteCount;
import com.example.Collaboration.entity.OurUsers;
import com.example.Collaboration.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote,Long> {

    @Query("SELECT NEW com.example.Collaboration.entity.ChoiceVoteCount(v.choice.id, count(v.id)) FROM Vote v WHERE v.proposal.id = :proposalId GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByProposalIdGroupByChoiceId(@Param("proposalId") Long proposalId);


    @Query("SELECT v FROM Vote v WHERE v.proposal.id = :proposalId AND v.user.id = :userId")
    Vote findByProposalIdAndUserId(@Param("proposalId") Long proposalId, @Param("userId") Integer userId);

    @Query("SELECT v FROM Vote v WHERE v.proposal.id = :proposalId")
    List<Vote> findByProposalId(@Param("proposalId") Long proposalId);

    List<Vote> findByChoiceId(Long id);

    List<OurUsers> findUsersByProposalId(Long proposalId);

    @Query("SELECT COALESCE(SUM(v.userScore), 0) FROM Vote v WHERE v.choice.id = :choiceId")
    Integer calculateTotalScoreForChoice(@Param("choiceId") Long choiceId);

    int countByProposalId(Long proposalId);

    int countByChoiceId(Long id);

    int countDistinctVotersByProposalId(Long id);
}
