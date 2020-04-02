package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.StudentMapper;
import com.hjs.system.model.Student;
import com.hjs.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/4 12:42
 * @Modified By:
 */

@CacheConfig(cacheNames = "student")
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int addStudent(Student student) {
        return studentMapper.insertStudent(student);
    }


    //Spring Cache默认是不支持在@Cacheable上添加过期时间的，可以在配置缓存容器时统一指定
    @Cacheable
    @Override
    public Student findStudentByStudentId(String studentId) {
        return studentMapper.findStudentByStudentId(studentId);
    }


    @Cacheable
    @Override
    public Student findStudentBySid(Integer sid) {
        return studentMapper.findStudentBySid(sid);
    }


    @Cacheable
    @Override
    public Page<Student> findStudentByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return studentMapper.findAllStudent();
    }


    @Override
    public Page<Student> fuzzyQueryStudentByPage(String search, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return studentMapper.fuzzyQueryAllStudent(search);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updatePassword(Integer sid, String password) {
        return studentMapper.updatePassword(sid, password);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateProfile(student);
    }


    @Override
    public boolean check(String studentId, String password) {
        Student student = studentMapper.findStudentByStudentId(studentId);
        if (student != null)
            if (student.getPassword().equals(password))
                return true;
        return false;
    }


    @Override
    public boolean exists(String studentId) {
        Student student = studentMapper.findStudentByStudentId(studentId);
        if (student != null)
            return true;
        return false;
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteStudent(Integer sid) {
        return studentMapper.deleteStudentBySid(sid);
    }
}
