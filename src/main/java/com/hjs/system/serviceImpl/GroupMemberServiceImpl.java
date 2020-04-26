package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.GroupMemberMapper;
import com.hjs.system.model.GroupMember;
import com.hjs.system.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/21 23:47
 * @Modified By:
 */

@CacheConfig(cacheNames = "groupMember")
@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberMapper groupMemberMapper;


    @Cacheable
    @Override
    public Page<GroupMember> findGroupMemberByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return groupMemberMapper.findAllGroupMember();
    }


    @Cacheable
    @Override
    public Page<GroupMember> findGroupMemberByGroupId(Integer groupId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return groupMemberMapper.findGroupMemberByGroupId(groupId);
    }


    @Cacheable
    @Override
    public Page<GroupMember> findGroupMemberByStudentId(Integer studentId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return groupMemberMapper.findGroupMemberByStudentId(studentId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteGroupMemberByGroupMemberId(Integer groupMemberId) {
        return groupMemberMapper.deleteGroupMemberByGroupMemberId(groupMemberId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertGroupMember(GroupMember record) {
        return groupMemberMapper.insertGroupMember(record);
    }


    @Cacheable
    @Override
    public GroupMember findGroupMemberByGroupMemberId(Integer groupMemberId) {
        return groupMemberMapper.findGroupMemberByGroupMemberId(groupMemberId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateGroupMember(GroupMember record) {
        return groupMemberMapper.updateGroupMember(record);
    }


}
