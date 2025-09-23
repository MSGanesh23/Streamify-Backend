package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersManager {

    @Autowired
    JWTManager JWT;

    @Autowired
    UsersRepository UR;

    public String addUser(Users u) {
        if(UR.validateEmail(u.getEmail())>0)
            return "401::Email already Exist";
        UR.save(u);
        return "200::User Registration Successful";
    }

    public String validateCredentials(String username, String password) {
        if(UR.validateCredentials(username, password)>0) {
            String token = JWT.generateToken(username);
            return "200::"+token+"::"+UR.findByusername(username).role;
        }
        return "401::Invalid Credentials";
    }

    public String getFullname(String token) {
        String username = JWT.validateToken(token);
        if(username.equals("401")) return "401::Token Expired";
        Users u = UR.findByusername(username);
        return u.getFullname();
    }
}
