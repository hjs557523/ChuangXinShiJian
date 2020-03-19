package com.hjs.system.mapper;

import com.hjs.system.model.ClassMember;

public interface ClassMemberMapper {
    int deleteByPrimaryKey(Integer classMemberId);

    int insert(ClassMember record);

    int insertSelective(ClassMember record);

    ClassMember selectByPrimaryKey(Integer classMemberId);

    int updateByPrimaryKeySelective(ClassMember record);

    int updateByPrimaryKey(ClassMember record);
}