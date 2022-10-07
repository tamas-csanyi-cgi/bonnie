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

    @GetMapping(path = "/register/{username}/{email}")
    public ResponseEntity<String> createUser(@PathVariable String username, @PathVariable String email) {
        long id = userService.createUser(username, email, "secret", Role.ASSEMBLER);
        return ResponseEntity.ok(""+id);
    }
}
