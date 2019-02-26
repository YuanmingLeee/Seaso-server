package com.seaso.seaso.modules.user.dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age}")
    int insert(@Param("name") String name, @Param("age") Integer age);
}
