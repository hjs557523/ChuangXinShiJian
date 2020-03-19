package com.hjs.system.mapper;

import com.hjs.system.model.GroupMember;

public interface GroupMemberMapper {
    int deleteByPrimaryKey(Integer groupMemberId);

    int insert(GroupMember record);

    int insertSelective(GroupMember record);

    GroupMember selectByPrimaryKey(Integer groupMemberId);

    int updateByPrimaryKeySelective(GroupMember record);

    int updateByPrimaryKey(GroupMember record);
}