package com.seaso.seaso;

import com.seaso.seaso.common.utils.IdGeneratingService;
import com.seaso.seaso.common.utils.Snowflake;
import org.junit.Test;

import java.util.List;

public class CommonUtilsTest {

    @Test
    public void testSnowflake() {
        Snowflake snowflake = new Snowflake(1, 2),
                snowflake2 = new Snowflake(1, 3),
                snowflake3 = new Snowflake(2, 2);
        IdGeneratingService idGeneratingService = new IdGeneratingService(snowflake, 5);
        IdGeneratingService idGeneratingService2 = new IdGeneratingService(snowflake2, 5);
        IdGeneratingService idGeneratingService3 = new IdGeneratingService(snowflake3, 5);

        List<Long> ids1, ids2, ids3;
        RuntimeException e = new RuntimeException("Interrupted when generating IDs");
        ids1 = idGeneratingService.generateIds(10).orElseThrow(() -> e);
        ids2 = idGeneratingService2.generateIds(10).orElseThrow(() -> e);
        ids3 = idGeneratingService3.generateIds(10).orElseThrow(() -> e);

        ids1.stream().map(id -> "id from server1 => " + id).forEach(System.out::println);
        ids2.stream().map(id -> "id from server2 => " + id).forEach(System.out::println);
        ids3.stream().map(id -> "id from server3 => " + id).forEach(System.out::println);

        idGeneratingService.stop();
        idGeneratingService2.stop();
        idGeneratingService3.stop();
    }
}
