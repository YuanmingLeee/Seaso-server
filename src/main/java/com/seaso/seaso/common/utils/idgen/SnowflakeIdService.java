package com.seaso.seaso.common.utils.idgen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Qualifier("snowflake")
public class SnowflakeIdService extends IdService {

    public SnowflakeIdService(@Qualifier("snowflake") IdGeneratable idGenerator,
                              @Value("${idgen.cache}") int cacheSize) {
        super(idGenerator, cacheSize);
    }
}
