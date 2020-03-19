package com.hjs.system.mapper;

import com.hjs.system.model.Class;

public interface ClassMapper {
    int deleteByPrimaryKey(Boolean cid);

    int insert(Class record);

    int insertSelective(Class record);

    Class selectByPrimaryKey(Boolean cid);

    int updateByPrimaryKeySelective(Class record);

    int updateByPrimaryKey(Class record);
}