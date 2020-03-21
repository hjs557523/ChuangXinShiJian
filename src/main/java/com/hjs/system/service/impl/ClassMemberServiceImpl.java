package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.ClassMemberMapper;
import com.hjs.system.model.ClassMember;
import com.hjs.system.service.ClassMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/21 17:52
 * @Modified By:
 */

@CacheConfig(cacheNames = "classMember")
@Service
public class ClassMemberServiceImpl implements ClassMemberService {

    @Autowired
    private ClassMemberMapper classMemberMapper;


    @Cacheable
    @Override
    public Page<ClassMember> findClassMemberByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return classMemberMapper.findAllClassMember();
    }


    @Cacheable
    @Override
    public Page<ClassMember> findClassMemberByClassId(Integer classId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return classMemberMapper.findClassMemberByClassId(classId);
    }


    @Cacheable
    @Override
    public Page<ClassMember> findClassMemberByStudentId(Integer studentId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return classMemberMapper.findClassMemberByStudentId(studentId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteClassMemberByClassMemberId(Integer classMemberId) {
        return classMemberMapper.deleteClassMemberByClassMemberId(classMemberId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertClassMember(ClassMember record) {
        return classMemberMapper.insertClassMember(record);
    }


    @Cacheable
    @Override
    public ClassMember findClassMemberByClassMemberId(Integer classMemberId) {
        return classMemberMapper.findClassMemberByClassMemberId(classMemberId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateClassMember(ClassMember record) {
        return classMemberMapper.updateClassMember(record);
    }


}
