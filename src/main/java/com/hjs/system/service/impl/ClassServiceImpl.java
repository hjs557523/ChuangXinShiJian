package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjs.system.mapper.ClassMapper;
import com.hjs.system.model.Class;
import com.hjs.system.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/21 15:52
 * @Modified By:
 */

@Service
@CacheConfig(cacheNames = "class")
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteClassByCid(Integer cid) {
        return classMapper.deleteClassByCid(cid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertClass(Class record) {
        return classMapper.insertClass(record);
    }


    @Cacheable
    @Override
    public Class findClassByCid(Integer cid) {
        return classMapper.findClassByCid(cid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateClass(Class record) {
        return classMapper.updateClass(record);
    }


    @Cacheable
    @Override
    public Page<Class> findClassByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return classMapper.findAllClass();
    }


    @Cacheable
    @Override
    public Page<Class> findClassByTid(Integer tid, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return classMapper.findClassByTid(tid);
    }


    @Cacheable
    @Override
    public Page<Class> findClassByCourseId(Integer courseId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return classMapper.findClassByCourseId(courseId);
    }


    @Cacheable
    @Override
    public Page<Class> findClassByIsFinished(Boolean isFinished, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return classMapper.findClassByIsFinished(isFinished);
    }

}
