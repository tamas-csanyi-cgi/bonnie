package com.cgi.hexagon.businessrules;

import com.cgi.hexagon.businessrules.user.IUserService;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class UserServiceTest {

    IUserService iUserService;

    UserService userService;

    @BeforeEach
    public void setup() {
        iUserService = Mockito.mock(IUserService.class);

        userService = new UserService(iUserService);
    }

    @Test
    public void expectLoadUserCallsLoad() {
        userService.loadUser(1L);

        verify(iUserService).load(1L);
    }

    @Test
    public void expectCreateUserCallsCreateUser() {
        final String name = "name";
        final String password = "password";
        final Role role = Role.ADMIN;

        userService.createUser(name, password, role);

        verify(iUserService).createUser(name, password, role);
    }

    @Test
    public void expectSaveCallsSave() {
        User user = new User(1L, "name", "password", Role.ADMIN);

        userService.save(user);

        verify(iUserService).save(user);
    }
}
