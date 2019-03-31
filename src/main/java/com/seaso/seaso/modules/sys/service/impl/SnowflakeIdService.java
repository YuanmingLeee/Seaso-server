package com.seaso.seaso.modules.sys.service.impl;

import com.seaso.seaso.common.utils.idgen.IdGeneratable;
import com.seaso.seaso.common.utils.idgen.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("snowflake")
public class SnowflakeIdService extends IdService {

    @Autowired
    public SnowflakeIdService(@Qualifier("snowflake") IdGeneratable idGenerator) {
        super(idGenerator, 1024);
    }
}
