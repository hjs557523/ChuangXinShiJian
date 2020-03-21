package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface TeacherMapper {

    // 1: 成功 0：失败
    int deleteTeacherByTid(Integer tid);

    Teacher findTeacherByTid(Integer tid);

    Teacher findTeacherByTeacherId(String teacherId);

    //需要在xml那边设置返回主键
    int insertTeacher(Teacher record);

    //update操作返回值是记录的 matched (匹配)的条数
    int updateProfile(Teacher record);

    Page<Teacher> findAllTeacher();

    Page<Teacher> fuzzyQueryAllTeacher(String value);

    int updatePassword(@Param("tid")Integer tid, @Param("password") String password);

}