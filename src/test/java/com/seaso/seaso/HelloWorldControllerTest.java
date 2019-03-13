package com.seaso.seaso;

import com.seaso.seaso.modules.sys.controller.HelloWorldController;
import com.seaso.seaso.modules.sys.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldControllerTest {

    private MockMvc mvc;

    @Autowired
    private HelloWorldController helloWorldController;

    @Autowired
    private UserController userController;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(helloWorldController, userController).build();
    }

    @Test
    public void testSayHello() throws Exception {
        mvc.perform(get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello, World!")));
    }

    @Test
    public void testUserController() throws Exception {
        RequestBuilder request = null;

        request = get("/users/");

        mvc.perform(request).andExpect(content().string("[]"));

        request = post("/users/")
                .param("id", "1")
                .param("name", "test name")
                .param("age", "20");

        mvc.perform(request).andExpect(content().string(equalTo("success")));

        request = get("/users/");

//        mvc.perform(request).andExpect(content().string(equalTo(
//                "[{\"id\":1,\"name\":\"test name\",\"age\":20}]"
//        )));
    }

    @Test
    public void testUserService() {
    }

    @Test
    public void testUserDao() {
    }
}
