package so.debateit.api.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import so.debateit.common.model.User;
import so.debateit.config.SecurityConfiguration;
import so.debateit.domain.application.UserService;
import so.debateit.util.JsonUtil;
import so.debateit.web.apis.RegistrationApiController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {SecurityConfiguration.class, RegistrationApiController.class})
@WebMvcTest(UserControllerTest.class)
@Import({RegistrationApiController.class})
public class UserControllerTest
{
    @Autowired
    MockMvc mvc;

    @MockBean
    UserService service;

    //@Test
    public void register_return400() throws Exception {
        User user = new User();
        System.out.println("JSON:::" + JsonUtil.toJsonString(user));
        mvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonString(user))).andExpect(status().is(200));
    }
}
