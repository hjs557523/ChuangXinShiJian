package com.hjs.system.service.impl;

import com.hjs.system.SystemApplication;
import com.hjs.system.mapper.StudentMapper;
import com.hjs.system.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 23:48
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
class TeacherServiceImplTest {

    @Autowired
    private TeacherService teacherServiceImpl;

    @Test
    void findTeacherByTeacherId() {
        //redis "student"库: key: com.hjs.system.service.impl.TeacherServiceImplfindTeacherByTeacherId13141314
        teacherServiceImpl.findTeacherByTeacherId("13141314");

    }
}