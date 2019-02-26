package com.seaso.seaso;

import com.seaso.seaso.modules.user.controller.HelloWorldController;
import com.seaso.seaso.modules.user.controller.UserController;
import com.seaso.seaso.modules.user.dao.UserDao;
import com.seaso.seaso.modules.user.entity.User;
import com.seaso.seaso.modules.user.service.UserService;
import org.junit.Assert;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(helloWorldController, userController).build();
        userService.deleteAllUsers();
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

        mvc.perform(request).andExpect(content().string(equalTo(
                "[{\"id\":1,\"name\":\"test name\",\"age\":20}]"
        )));
    }

    @Test
    public void testUserService() {
        userService.create("a", 1);
        userService.create("b", 2);
        userService.create("c", 3);

        Assert.assertEquals(3, userService.getAllUsers().intValue());

        userService.deleteByName("a");
        userService.deleteByName("b");

        Assert.assertEquals(1, userService.getAllUsers().intValue());
    }

    @Test
    public void testUserDao() {
        userDao.save(new User("AAA", 10));
        userDao.save(new User("BBB", 20));
        userDao.save(new User("CCC", 30));

        Assert.assertEquals(3, userDao.findAll().size());

        Assert.assertEquals(20, userDao.findByName("BBB").getAge().intValue());

        userDao.delete(userDao.findByName("AAA"));

        Assert.assertEquals(2, userDao.findAll().size());
    }
}
