package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.ProjectTypeMapper;
import com.hjs.system.model.ProjectType;
import com.hjs.system.service.ProjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/21 22:13
 * @Modified By:
 */

@CacheConfig(cacheNames = "projectType")
@Service
public class ProjectTypeImpl implements ProjectTypeService {

    @Autowired
    private ProjectTypeMapper projectTypeMapper;

    @Cacheable
    @Override
    public Page<ProjectType> findProjectTypeByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return projectTypeMapper.findAllProjectType();
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteProjectTypeByPid(Integer pid) {
        return projectTypeMapper.deleteProjectTypeByPid(pid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertProjectType(ProjectType record) {
        return projectTypeMapper.insertProjectType(record);
    }


    @Cacheable
    @Override
    public ProjectType findProjectTypeByPid(Integer pid) {
        return projectTypeMapper.findProjectTypeByPid(pid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateProjectType(ProjectType record) {
        return projectTypeMapper.updateProjectType(record);
    }

}
