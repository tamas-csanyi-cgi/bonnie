package com.cgi.bonnie.bonnierest;

import com.cgi.bonnie.bonnierest.model.UserRequest;
import com.cgi.bonnie.businessrules.Role;
import com.cgi.bonnie.businessrules.user.User;
import com.cgi.bonnie.businessrules.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class.getName());

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        try {
            User user = userService.loadUser(id);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            log.debug("can't find new user: "+id+" ("+e.getMessage()+")");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/current/name")
    public ResponseEntity<String> getCurrentUsername() {
        try {
            String name = userService.getCurrentUsername();
            return ResponseEntity.ok(name);
        }catch (Exception e) {
            log.debug("can't find current user username: "+e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/current/email")
    public ResponseEntity<String> getCurrentUserEmail() {
        try {
            String name = userService.getCurrentUserEmail();
            return ResponseEntity.ok(name);
        }catch (Exception e) {
            log.debug("can't find current user email: "+e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        if (null == user) {
            log.debug("can't find current user in DB: ");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    //for testing purposes
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        }catch (Exception e) {
            log.debug("can't get list of current users: "+e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
        Role role1 = Role.valueOf(request.getRole().toUpperCase());
        long id = userService.createUser(request.getName(), request.getEmail(), request.getPassword(), role1);
        return ResponseEntity.ok(""+id);
    }
}
