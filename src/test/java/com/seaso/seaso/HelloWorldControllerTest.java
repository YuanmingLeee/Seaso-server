package com.seaso.seaso;

import com.seaso.seaso.modules.sys.controller.HelloWorldController;
import com.seaso.seaso.modules.sys.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    public void testSayHello() {
    }

    @Test
    public void testUserController() {
    }

    @Test
    public void testUserService() {
    }

    @Test
    public void testUserDao() {
    }
}
