package com.seaso.seaso;

import com.seaso.seaso.common.utils.idgen.IdService;
import com.seaso.seaso.common.utils.idgen.Snowflake;
import org.junit.Test;

import java.util.List;

public class CommonUtilsTest {

    @Test
    public void testSnowflake() {
        Snowflake snowflake = new Snowflake(1, 2),
                snowflake2 = new Snowflake(1, 3),
                snowflake3 = new Snowflake(2, 2);
        IdService idService = new IdService(snowflake, 5);
        IdService idService2 = new IdService(snowflake2, 5);
        IdService idService3 = new IdService(snowflake3, 5);

        List<Long> ids1, ids2, ids3;
        ids1 = idService.generateIds(10);
        ids2 = idService2.generateIds(10);
        ids3 = idService3.generateIds(10);

        ids1.stream().map(id -> "id from server1 => " + id).forEach(System.out::println);
        ids2.stream().map(id -> "id from server2 => " + id).forEach(System.out::println);
        ids3.stream().map(id -> "id from server3 => " + id).forEach(System.out::println);

        idService.stop();
        idService2.stop();
        idService3.stop();
    }
}
