package com.example.Collaboration.repository;

import com.example.Collaboration.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    Optional<Proposal> findById(Long id);
    List<Proposal> findByStatusAndExpirationDateTimeBefore(Proposal.Status status, Instant dateTime);

}
