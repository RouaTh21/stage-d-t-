package com.example.Collaboration.repository;

import com.example.Collaboration.entity.OurUsers;
import com.example.Collaboration.entity.UserGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupMemberRepository extends JpaRepository<UserGroupMember, Long> {

    List<UserGroupMember> findByProposalIdAndUserId(Long proposalId, Integer userId);

}
