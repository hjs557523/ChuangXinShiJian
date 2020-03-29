package com.hjs.system.base.utils;

import com.hjs.system.SystemApplication;
import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.StudentService;
import com.hjs.system.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/28 14:10
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
class JSONUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    @Autowired
    private TeacherService teacherServiceImpl;

    @Autowired
    private StudentService studentServiceImpl;

    @Test
    void returnEntityResult() {
        Student student = studentServiceImpl.findStudentByStudentId("16041321");
        Teacher teacher = teacherServiceImpl.findTeacherByTeacherId("13141314");
        Map<String, Object> map = new HashMap<>();
        map.put("student", student);
        map.put("teacher", teacher);
        logger.info(JSONUtil.returnEntityResult(map));

        String a = "hjs";
        String b = "hjs";
        if (a == b)
            logger.info("true");

    }


}