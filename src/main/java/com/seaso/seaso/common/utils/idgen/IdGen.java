package com.seaso.seaso.common.utils.idgen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * This class is an ID generator which generates default 64-bit {@link Long} type ID using twitter snowflake algorithm
 */
@Component
public class IdGen {
    private static IdService idService;

    private final IdService _idService;


    @Autowired
    public IdGen(@Qualifier("snowflake") IdService idService) {
        _idService = idService;
    }

    public static List<Long> generateIds(int size) {
        return idService.generateIds(size);
    }

    public static Long generateId() {
        return idService.generateIds(1).get(0);
    }

    @PostConstruct
    public void init() {
        idService = _idService;
    }
}
