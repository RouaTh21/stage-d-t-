package com.example.Collaboration.service;

import com.example.Collaboration.dto.ReqRes;
import com.example.Collaboration.entity.OurUsers;
import com.example.Collaboration.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ReqRes getAllUsers() {
        ReqRes response = new ReqRes();
        try {
            List<OurUsers> users = ourUserRepo.findAll();
            if (users.isEmpty()) {
                response.setStatusCode(204);
                response.setMessage("No users found");
            } else {
                response.setStatusCode(200);
                response.setUsers(users);
                response.setMessage("Successfully fetched all users");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes deleteUser(Integer userId) {
        ReqRes response = new ReqRes();
        try {
            if (ourUserRepo.existsById(userId)) {
                ourUserRepo.deleteById(userId);
                response.setStatusCode(200);
                response.setMessage("User successfully deleted");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }


    public ReqRes updateUser(Integer userId, OurUsers updatedUser) {
        ReqRes response = new ReqRes();
        try {
            if (ourUserRepo.existsById(userId)) {
                OurUsers existingUser = ourUserRepo.findById(userId).orElse(null);
                if (existingUser != null) {
                    // Encrypt the password before updating the user
                    String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
                    updatedUser.setPassword(encodedPassword);


                    // Ensure the role has the ROLE_ prefix
                    String role = updatedUser.getRole();
                    if (role != null && !role.startsWith("ROLE_")) {
                        updatedUser.setRole("ROLE_" + role.toUpperCase());
                    }

                    updatedUser.setId(userId);
                    ourUserRepo.save(updatedUser);
                    response.setStatusCode(200);
                    response.setMessage("User successfully updated");
                } else {
                    response.setStatusCode(404);
                    response.setMessage("User not found");
                }
            }else{
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }


}
