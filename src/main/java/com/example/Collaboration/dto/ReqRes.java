package com.example.Collaboration.dto;

import com.example.Collaboration.entity.OurUsers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private String role;
    private String username;
    private String password;
    private OurUsers ourUsers;
    private List<OurUsers> users;


    public List<OurUsers> getUsers() {
        return users;
    }
    public void setUsers(List<OurUsers> users) {
        this.users = users;
    }


}
