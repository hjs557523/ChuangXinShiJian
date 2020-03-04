package com.hjs.system.service.impl;

import com.hjs.system.mapper.StudentMapper;
import com.hjs.system.model.Student;
import com.hjs.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/4 12:42
 * @Modified By:
 */

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void saveStudent(Student student) {
        studentMapper.save(student);
    }

    @Override
    public Student queryStudent(String studentId) {
        return studentMapper.findStudentByStudentId(studentId);
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

    @Override
    public void deleteStudent(Integer studentId) {
        studentMapper.delete(studentId);
    }
}
