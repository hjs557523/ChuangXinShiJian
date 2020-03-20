package com.hjs.system.mapper;

import com.hjs.system.model.Group;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupMapper {
    int deleteByPrimaryKey(Integer gid);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer gid);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
}