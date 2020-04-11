package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Student;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/4 12:33
 * @Modified By:
 */
public interface StudentService {

    int addStudent(Student student);

    Student findStudentByGitHubName(String githubName);

    Student findStudentByStudentId(String studentId);

    Student findStudentBySid(Integer sid);

    Page<Student> findStudentByPage(int pageNo, int pageSize);

    Page<Student> fuzzyQueryStudentByPage(String search, int pageNo, int pageSize);

    int updatePassword(Integer sid, String password);

    int updateStudent(Student student);

    boolean check(String studentId, String password);

    boolean exists(String studentId);

    int deleteStudent(Integer sid);
}
