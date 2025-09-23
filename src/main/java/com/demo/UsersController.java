package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    UsersManager UM;

    @PostMapping("/register")
    public String register(@RequestBody Users u) {
        return UM.addUser(u);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users u) {
        return UM.validateCredentials(u.getUsername(), u.getPassword());
    }

    @PostMapping("/getfullname")
    public String getFullname(@RequestBody Map<String,String> data) {
        return UM.getFullname(data.get("csrid"));
    }
}
