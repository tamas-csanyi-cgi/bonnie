package com.cgi.bonnie;

import com.cgi.bonnie.businessrules.Role;
import com.cgi.bonnie.businessrules.user.User;
import com.cgi.bonnie.businessrules.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIT extends BaseIT {

    private static final String PATH_USER_GET = "/api/user/";
    private static final String TEST_USER_NAME = "test user";
    private static final String TEST_USER_EMAIL = "testuser@mail.local";
    private static final String TEST_USER_PASSWORD = "testpassword";
    private static final Role TEST_USER_ROLE = Role.ADMIN;
    private static final long UNKNOWN_USER_ID = 9999L;

    @Autowired
    private UserService userService;

    @Test
    @WithMockUser
    void getUser_noUserPresent_badRequest() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_USER_GET + UNKNOWN_USER_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertNotNull(result);
    }

    @Test
    @WithMockUser
    void getUser_userPresent_returnsUser() throws Exception {
        final long id = userService.createUser(TEST_USER_NAME, TEST_USER_EMAIL, TEST_USER_PASSWORD, TEST_USER_ROLE);

        final MvcResult result = mockMvc.perform(get(PATH_USER_GET + id))
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
}
