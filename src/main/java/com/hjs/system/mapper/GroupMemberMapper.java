package com.hjs.system.mapper;

import com.hjs.system.model.GroupMember;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupMemberMapper {

    int deleteGroupMemberByGroupMemberId(Integer groupMemberId);

    int insertGroupMember(GroupMember record);

    GroupMember findGroupMemberByGroupMemberId(Integer groupMemberId);

    int updateGroupMember(GroupMember record);

}