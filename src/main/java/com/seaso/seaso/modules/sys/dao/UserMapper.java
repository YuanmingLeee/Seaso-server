package com.seaso.seaso.modules.sys.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age}")
    int insert(@Param("name") String name, @Param("age") Integer age);
}
