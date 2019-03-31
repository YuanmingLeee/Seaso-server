package com.seaso.seaso;

import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.utils.JsonResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @LocalServerPort
    int port;
    private TestRestTemplate testRestTemplate = new TestRestTemplate("admin", "Seaso@2019");
    private String baseUri;

    @Before
    public void setup() {
        baseUri = "http://localhost:" + port + "/users";
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateUser() {
        User user = new User();
        user.setUsername("test_UserControllerTest");
        user.setAge(20);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<User> userHttpEntity = new HttpEntity<>(user, headers);

        ResponseEntity<JsonResponse> userResponseEntity = testRestTemplate.postForEntity(baseUri + "/", userHttpEntity,
                JsonResponse.class);

        Assert.assertEquals(200, userResponseEntity.getStatusCodeValue());
        Assert.assertTrue(userResponseEntity.hasBody());
        JsonResponse json = userResponseEntity.getBody();
        Assert.assertEquals(200, json.getStatusCode());
    }
}
