package com.hjs.system.mapper;

import com.hjs.system.model.Role;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}