package com.example.Collaboration.controller;

import com.example.Collaboration.dto.VoteRequest;
import com.example.Collaboration.entity.OurUsers;
import com.example.Collaboration.service.ProposalService;
import com.example.Collaboration.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;


    @PostMapping("/createavote/{proposalId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> cVote(@PathVariable Long proposalId,
                                   @Valid @RequestBody VoteRequest voteRequest,
                                   @AuthenticationPrincipal OurUsers userDetails){

        return voteService.castVote(proposalId,voteRequest, userDetails.getId());

    }
}
