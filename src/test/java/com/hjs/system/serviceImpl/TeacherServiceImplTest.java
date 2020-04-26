package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjs.system.SystemApplication;
import com.hjs.system.mapper.ClassMapper;
import com.hjs.system.model.Class;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 23:48
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
class TeacherServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImplTest.class);

    @Autowired
    private TeacherService teacherServiceImpl;

    @Autowired
    private ClassMapper classMapper;

    @Test
    void findTeacherByTeacherId() {
        //redis "student"库: key: com.hjs.system.service.impl.TeacherServiceImplfindTeacherByTeacherId13141314
        teacherServiceImpl.findTeacherByTeacherId("13141314");

    }

    @Test
    void test2() {
        Page<Teacher> page = teacherServiceImpl.findTeacherByPage(3,1);
        PageInfo<Teacher> pageInfo = new PageInfo<>(page);
        //数据库当时只有3条数据
        logger.info(String.valueOf(pageInfo.getSize()));//1
        logger.info(String.valueOf(pageInfo.getTotal()));//3
        logger.info(String.valueOf(page.getTotal()));//3

    }


    @Test
    void test3() {
        Page page = PageHelper.startPage(1,1);
        List<Class> classes = classMapper.findClassByTid2(1);
        PageInfo<Class> classPageInfo = new PageInfo<>(classes);
        logger.info(String.valueOf(page.getTotal()));//2
        logger.info(String.valueOf(classes.size()));//1
        logger.info(String.valueOf(classPageInfo.getTotal()));//2
        logger.info(String.valueOf(classPageInfo.getSize()));//1
    }
}