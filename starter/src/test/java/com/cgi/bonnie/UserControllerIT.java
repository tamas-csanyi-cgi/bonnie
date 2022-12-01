package com.cgi.bonnie;

import com.cgi.bonnie.bonnierest.model.UserRequest;
import com.cgi.bonnie.businessrules.user.User;
import com.cgi.bonnie.businessrules.user.UserCredentials;
import com.cgi.bonnie.businessrules.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIT extends BaseIT {

    private static final String PATH_USER_ROOT = "/api/user";
    private static final String PATH_USER_ID = PATH_USER_ROOT + "/{id}";
    private static final String PATH_CURRENT_USERNAME = PATH_USER_ROOT + "/current/name";
    private static final String PATH_CURRENT_EMAIL = PATH_USER_ROOT + "/current/email";
    private static final String PATH_CURRENT_USER = PATH_USER_ROOT + "/current";
    private static final String PATH_USER_GET = PATH_USER_ROOT + "/";
    private static final String PATH_USER_CREATE = PATH_USER_ROOT + "/add";
    private static final long UNKNOWN_USER_ID = 9999L;

    @Autowired
    private UserService userService;

    @Test
    void getUser_noUserPresent_badRequest() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_USER_ID, UNKNOWN_USER_ID)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertNotNull(result);
    }

    @Test
    void getUser_userPresent_returnsUser() throws Exception {
        final long id = userService.createUser(TEST_USER_NAME, TEST_USER_EMAIL, TEST_USER_PASSWORD, TEST_USER_ROLE);

        final MvcResult result = mockMvc.perform(get(PATH_USER_ID, id)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());

        final User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

        assertEquals(id, user.getId());
        assertEquals(TEST_USER_NAME, user.getName());
        assertEquals(TEST_USER_EMAIL, user.getEmail());
        assertEquals(TEST_USER_ROLE, user.getRole());
    }

    @Test
    void getCurrentUsername_unAuthenticated_forbidden() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_CURRENT_USERNAME))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();

        assertNotNull(result);
    }

    @Test
    void getCurrentUsername_authenticated_returnsUsername() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_CURRENT_USERNAME)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(userData.getName(), result.getResponse().getContentAsString());
    }

    @Test
    void getCurrentEmail_unAuthenticated_forbidden() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_CURRENT_EMAIL))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();

        assertNotNull(result);
    }

    @Test
    void getCurrentEmail_authenticated_returnsEmail() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_CURRENT_EMAIL)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(userData.getEmail(), result.getResponse().getContentAsString());
    }

    @Test
    void getCurrentUser_unAuthenticated_forbidden() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_CURRENT_USER))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();

        assertNotNull(result);
    }

    @Test
    void getCurrentUser_authenticated_returnsUser() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_CURRENT_USER)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());

        final User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals(userData, user);
    }

    @Test
    void getAllUsers_noInputData_returnsUserList() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_USER_GET)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());

        final List<User> userList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<User>>() {});

        assertNotNull(userList);
        assertFalse(userList.isEmpty());
    }

    @Test
    void createUser_validInputData_userCreated() throws Exception {
        final UserRequest userRequest = new UserRequest(TEST_USER_NAME, TEST_USER_PASSWORD, TEST_USER_ROLE.name(), TEST_USER_EMAIL);
        final String body = objectMapper.writeValueAsString(userRequest);

        final MvcResult result = mockMvc.perform(post(PATH_USER_CREATE)
                        .content(body)
                        .contentType(APPLICATION_JSON)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());

        final long userId = Long.parseLong(result.getResponse().getContentAsString());
        final User user = userService.loadUser(userId);
        final UserCredentials userCredentials = userCredentialStorage.findByEmail(TEST_USER_EMAIL);

        assertEquals(userRequest.getName(), user.getName());
        assertEquals(userRequest.getEmail(), user.getEmail());
        assertEquals(userRequest.getRole(), user.getRole().name());
        assertEquals(userRequest.getName(), userCredentials.getName());
        assertEquals(userRequest.getEmail(), userCredentials.getEmail());
        assertEquals(userRequest.getPassword(), userCredentials.getPassword());
    }
}
