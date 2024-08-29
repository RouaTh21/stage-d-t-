package com.example.Collaboration.service;

import com.example.Collaboration.dto.ReqRes;
import com.example.Collaboration.entity.OurUsers;
import com.example.Collaboration.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            Optional<OurUsers> existingUser = ourUserRepo.findByEmail(registrationRequest.getEmail());
            if (existingUser.isPresent()) {
                resp.setMessage("User already exists");
                resp.setStatusCode(400);
            } else {
                OurUsers ourUsers = new OurUsers();
                ourUsers.setEmail(registrationRequest.getEmail());
                ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
                ourUsers.setUsername(registrationRequest.getUsername());
                // Ensure role is prefixed with ROLE_
                String role = registrationRequest.getRole() != null ? "ROLE_" + registrationRequest.getRole() : "ROLE_USER";
                ourUsers.setRole(role);

                OurUsers ourUserResult = ourUserRepo.save(ourUsers);
                if (ourUserResult != null && ourUserResult.getId() > 0) {
                    resp.setOurUsers(ourUserResult);
                    resp.setMessage("User Saved Successfully");
                    resp.setStatusCode(200);
                }
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(ReqRes signinRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        String userEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        OurUsers user = ourUserRepo.findByEmail(userEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        } else {
            response.setStatusCode(500);
            response.setMessage("Invalid Token");
        }
        return response;
    }
}
