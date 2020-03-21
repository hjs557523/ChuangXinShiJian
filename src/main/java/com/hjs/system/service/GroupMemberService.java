package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.GroupMember;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:30
 * @Modified By:
 */
public interface GroupMemberService {

    Page<GroupMember> findGroupMemberByPage(int pageNo, int pageSize);

    Page<GroupMember> findGroupMemberByGroupId(Integer groupId, int pageNo, int pageSize);

    Page<GroupMember> findGroupMemberByStudentId(Integer studentId, int pageNo, int pageSize);

    int deleteGroupMemberByGroupMemberId(Integer groupMemberId);

    int insertGroupMember(GroupMember record);

    GroupMember findGroupMemberByGroupMemberId(Integer groupMemberId);

    int updateGroupMember(GroupMember record);
}
