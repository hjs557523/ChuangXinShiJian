package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.SubjectMapper;
import com.hjs.system.model.Subject;
import com.hjs.system.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/21 21:13
 * @Modified By:
 */

@CacheConfig(cacheNames = "subject")
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;


    @Cacheable
    @Override
    public Page<Subject> findSubjectByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return subjectMapper.findAllSubject();
    }


    @Cacheable
    @Override
    public Page<Subject> findSubjectByTid(Integer tid, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return subjectMapper.findSubjectByTid(tid);
    }


    @Cacheable
    @Override
    public Page<Subject> findSubjectByTname(int pageNo, int pageSize, String name) {
        PageHelper.startPage(pageNo, pageSize);
        return subjectMapper.findSubjectByTname(name);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteSubjectBySubjectId(Integer subjectId) {
        return subjectMapper.deleteSubjectBySubjectId(subjectId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertSubject(Subject record) {
        return subjectMapper.insertSubject(record);
    }


    @Cacheable
    @Override
    public Subject findSubjectBySubjectId(Integer subjectId) {
        return subjectMapper.findSubjectBySubjectId(subjectId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateSubject(Subject record) {
        return subjectMapper.updateSubject(record);
    }
}
