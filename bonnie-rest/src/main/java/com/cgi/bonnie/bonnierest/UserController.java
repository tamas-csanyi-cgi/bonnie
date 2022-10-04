package com.cgi.bonnie.bonnierest;

import com.cgi.bonnie.bonnierest.model.UserRequest;
import com.cgi.bonnie.businessrules.Role;
import com.cgi.bonnie.businessrules.user.User;
import com.cgi.bonnie.businessrules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        try {
            User user = userService.loadUser(id);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
        Role role1 = Role.valueOf(request.getRole().toUpperCase());
        long id = userService.createUser(request.getName(), request.getPassword(), role1);
        return ResponseEntity.ok(""+id);
    }

    @PatchMapping(path = "/role/{userId}/{role}")
    public ResponseEntity<Boolean> changeUserRole(@PathVariable String userId, @PathVariable String role) {
        try {
            User user = userService.loadUser(Long.valueOf(userId));
            user.setRole(Role.valueOf(role.toUpperCase()));
            return ResponseEntity.ok(userService.save(user));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }


    }
}
