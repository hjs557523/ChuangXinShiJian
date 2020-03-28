package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.base.BaseDAO;
import com.hjs.system.model.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper extends BaseDAO<Student> {

    /**
     * 通过学号查询学生信息
     * @param studentId
     * @return
     */
    Student findStudentByStudentId(@Param("studentId") String studentId);

    int insertStudent(Student student);

    int updateProfile(Student student);

    int updatePassword(@Param("sid") Integer sid, @Param("password")String password);

    int deleteStudentBySid(Integer id);

    Student findStudentBySid(Integer id);

    Page<Student> findAllStudent();

    Page<Student> fuzzyQueryAllStudent(String value);


//    void batchSave(List<Student> list);
//
//    void batchDelete(List<Integer> idlist);
}