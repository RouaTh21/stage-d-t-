package com.example.Collaboration.controller;

import com.example.Collaboration.entity.UserGroupMember;
import com.example.Collaboration.service.UserGroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user-groups-members")
public class UserGroupMemberController {
    @Autowired
    private UserGroupMemberService userGroupMemberService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> createUserGroupMember(@RequestBody UserGroupMember userGroupMember) {
        try {
            UserGroupMember createdMember = userGroupMemberService.createUserGroupMember(userGroupMember);
            return ResponseEntity.status(HttpStatus.CREATED).body("Success! User group member created with ID: " + createdMember.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error! Could not create user group member: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserGroupMember> getUserGroupMember(@PathVariable Long id) {
        Optional<UserGroupMember> userGroupMember = userGroupMemberService.getUserGroupMemberById(id);
        return userGroupMember.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
