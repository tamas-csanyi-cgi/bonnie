package com.cgi.hexagon.bonnierest;

import com.cgi.hexagon.bonnierest.model.UserRequest;
import com.cgi.hexagon.businessrules.Role;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.UUID;

@RestController
@RequestMapping(value = "/asd")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        try {
            User user = userService.loadUser(id);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/user/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
        Role role1 = Role.valueOf(request.getRole().toUpperCase());
        long id = userService.createUser(request.getName(), request.getPassword(), role1);
        return ResponseEntity.ok(""+id);
    }
}
