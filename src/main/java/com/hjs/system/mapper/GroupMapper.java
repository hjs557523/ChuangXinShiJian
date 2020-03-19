package com.hjs.system.mapper;

import com.hjs.system.model.Group;

public interface GroupMapper {
    int deleteByPrimaryKey(Integer gid);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer gid);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
}