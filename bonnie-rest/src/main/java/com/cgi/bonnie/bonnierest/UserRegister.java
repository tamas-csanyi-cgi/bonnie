package com.cgi.bonnie.bonnierest;

import com.cgi.bonnie.businessrules.Role;
import com.cgi.bonnie.businessrules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserRegister {

    final private UserService userService;

    @Autowired
    public UserRegister(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/register/{username}")
    public ResponseEntity<String> createUser(@PathVariable String username) {
        long id = userService.createUser(username, "secret", Role.ASSEMBLER);
        return ResponseEntity.ok(""+id);
    }
}
