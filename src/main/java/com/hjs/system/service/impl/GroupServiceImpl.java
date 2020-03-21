package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.GroupMapper;
import com.hjs.system.model.Group;
import com.hjs.system.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/21 23:05
 * @Modified By:
 */

@CacheConfig(cacheNames = "group")
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;


    @Cacheable
    @Override
    public Page<Group> findGroupByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return groupMapper.findAllGroup();
    }


    @Cacheable
    @Override
    public Page<Group> findGroupByTid(Integer tid, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return groupMapper.findGroupByTid(tid);
    }


    @Cacheable
    @Override
    public Page<Group> findGroupByStateId(Integer stateId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return groupMapper.findGroupByStateId(stateId);
    }


    @Cacheable
    @Override
    public Page<Group> findGroupByOwnerId(Integer ownerId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return groupMapper.findGroupByOwnerId(ownerId);
    }


    @Cacheable
    @Override
    public Page<Group> findGroupBySubjectId(Integer subjectId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return groupMapper.findGroupBySubjectId(subjectId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteGroupByGid(Integer gid) {
        return groupMapper.deleteGroupByGid(gid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertGroup(Group record) {
        return groupMapper.insertGroup(record);
    }


    @Cacheable
    @Override
    public Group findGroupByGid(Integer gid) {
        return groupMapper.findGroupByGid(gid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateGroup(Group record) {
        return groupMapper.updateGroup(record);
    }
}
