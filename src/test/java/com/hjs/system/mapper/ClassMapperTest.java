package com.hjs.system.mapper;

import com.github.pagehelper.PageInfo;
import com.hjs.system.SystemApplication;
import com.hjs.system.model.Class;
import com.hjs.system.model.Course;
import com.hjs.system.model.Teacher;
import org.apache.catalina.LifecycleState;
import org.codehaus.groovy.reflection.ClassInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/4 17:20
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
class ClassMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(ClassMapperTest.class);

    @Autowired
    private ClassMapper classMapper;

    @Test
    void Test() {
        logger.info(classMapper.findClassByTid2(1).toString());
    }

    @Test
    void Test2() {
        Class classInfo = new Class();
        Teacher teacher = new Teacher();
        teacher.setTid(1);
        Course course = new Course();
        course.setCid(2);
        classInfo.setTeacher(teacher);
        classInfo.setCourse(course);
        classInfo.setIsFinished(false);
        classInfo.setClassName("秦福顺创新实践2");
        logger.info(""+classMapper.insertClass(classInfo));
    }
}