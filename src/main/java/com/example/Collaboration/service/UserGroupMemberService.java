package com.example.Collaboration.service;

import com.example.Collaboration.entity.UserGroupMember;
import com.example.Collaboration.repository.UserGroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGroupMemberService {
    @Autowired
    private UserGroupMemberRepository userGroupMemberRepository;

    public UserGroupMember createUserGroupMember(UserGroupMember userGroupMember) {
        return userGroupMemberRepository.save(userGroupMember);
    }

    public Optional<UserGroupMember> getUserGroupMemberById(Long id) {
        return userGroupMemberRepository.findById(id);
    }

}
