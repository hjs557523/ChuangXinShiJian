package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.GroupMember;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupMemberMapper {

    Page<GroupMember> findAllGroupMember();

    Page<GroupMember> findGroupMemberByGroupId(Integer groupId);

    Page<GroupMember> findGroupMemberByStudentId(Integer studentId);

    int deleteGroupMemberByGroupMemberId(Integer groupMemberId);

    int insertGroupMember(GroupMember record);

    GroupMember findGroupMemberByGroupMemberId(Integer groupMemberId);

    int updateGroupMember(GroupMember record);


}