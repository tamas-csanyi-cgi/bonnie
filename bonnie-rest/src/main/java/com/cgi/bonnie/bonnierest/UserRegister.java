package com.cgi.bonnie.bonnierest;

import com.cgi.bonnie.bonnierest.model.UserRequest;
import com.cgi.bonnie.businessrules.Role;
import com.cgi.bonnie.businessrules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserRegister {

    final private UserService userService;

    @Autowired
    public UserRegister(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createUser(UserRequest request) {
        long id = userService.createUser(request.getName(), request.getEmail(), request.getPassword(), Role.ASSEMBLER);
        return ResponseEntity.ok(""+id);
    }
}
