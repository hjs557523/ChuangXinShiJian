package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.RoleMapper;
import com.hjs.system.model.Role;
import com.hjs.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/21 21:39
 * @Modified By:
 */

@CacheConfig(cacheNames = "role")
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Cacheable
    @Override
    public Page<Role> findRoleByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return roleMapper.findAllRole();
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteRoleByRid(Integer rid) {
        return roleMapper.deleteRoleByRid(rid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertRole(Role record) {
        return roleMapper.insertRole(record);
    }


    @Cacheable
    @Override
    public Role findRoleByRid(Integer rid) {
        return roleMapper.findRoleByRid(rid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateRole(Role record) {
        return roleMapper.updateRole(record);
    }

}
