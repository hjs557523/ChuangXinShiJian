package com.hjs.system.service;

import com.hjs.system.model.Student;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/4 12:33
 * @Modified By:
 */
public interface StudentService {

    void saveStudent(Student student);

    Student queryStudent(String studentId);

    boolean check(String studentId, String password);

    boolean exists(String studentId);

    void deleteStudent(Integer sid);
}
