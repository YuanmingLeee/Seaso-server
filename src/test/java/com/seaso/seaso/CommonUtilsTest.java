package com.seaso.seaso;

import com.seaso.seaso.common.utils.IdGenerationServer;
import com.seaso.seaso.common.utils.Snowflake;
import org.junit.Test;

import java.util.List;

public class CommonUtilsTest {

    @Test
    public void testSnowflake() {
        Snowflake snowflake = new Snowflake(1, 2),
                snowflake2 = new Snowflake(1, 3),
                snowflake3 = new Snowflake(2, 2);
        IdGenerationServer idGenerationServer = new IdGenerationServer(snowflake, 5);
        idGenerationServer.start();
        IdGenerationServer idGenerationServer2 = new IdGenerationServer(snowflake2, 5);
        idGenerationServer2.start();
        IdGenerationServer idGenerationServer3 = new IdGenerationServer(snowflake3, 5);
        idGenerationServer3.start();

        List<Long> ids1, ids2, ids3;
        ids1 = idGenerationServer.generateIds(10);
        ids2 = idGenerationServer2.generateIds(10);
        ids3 = idGenerationServer3.generateIds(10);

        ids1.stream().map(id -> "id from server1 => " + id).forEach(System.out::println);
        ids2.stream().map(id -> "id from server2 => " + id).forEach(System.out::println);
        ids3.stream().map(id -> "id from server3 => " + id).forEach(System.out::println);

        idGenerationServer.stop();
        idGenerationServer2.stop();
        idGenerationServer3.stop();
    }
}
