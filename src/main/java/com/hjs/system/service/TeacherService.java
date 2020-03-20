package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Teacher;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:17
 * @Modified By:
 */
public interface TeacherService {

    Page<Teacher> findTeacherByPage(int pageNo, int pageSize);

    Page<Teacher> fuzzyQueryTeacherByPage(String search, int pageNo, int pageSize);

    Teacher findTeacherByTeacherId(String teacherId);

    Teacher findTeacherByTid(Integer tid);

    int updateTeacher(Teacher teacher);

    int updateProfile(Teacher teacher);

    int updatePassword(Integer tid, String password);

    int updatePicImg(Integer tid, String path);

    int addTeacher(Teacher teacher);

    int deleteTeacherByTid(Integer tid);
}
