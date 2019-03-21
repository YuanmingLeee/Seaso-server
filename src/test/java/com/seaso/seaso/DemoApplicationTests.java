package com.seaso.seaso;

import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void authenticationTest() {
        // get your root BCrypt coeducation here
        System.out.println(UserUtils.encryptByBCrypt("Seaso@2019"));
    }
}

