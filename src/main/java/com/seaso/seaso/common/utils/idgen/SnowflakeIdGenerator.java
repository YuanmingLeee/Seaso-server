package com.seaso.seaso.common.utils.idgen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("snowflake")
public class SnowflakeIdGenerator extends Snowflake {

    @Autowired
    public SnowflakeIdGenerator(@Value("${idgen.data-center-id}") long dataCenterId,
                                @Value("${idgen.machine-id}") long machineId) {
        super(dataCenterId, machineId);
    }
}
