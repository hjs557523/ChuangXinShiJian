package com.hjs.system.mapper;

import com.hjs.system.SystemApplication;
import com.hjs.system.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/19 18:20
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
class TeacherMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(TeacherMapperTest.class);

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    void selectByTeacherIdTest() {
        logger.info("测试结果: {}", teacherMapper.findTeacherByTeacherId("13141314"));
    }


    @Test
    void insertTest() {
        Teacher teacher = new Teacher();
        teacher.setRealName("胡歌");
        teacher.setPassword("huge");
        logger.info("测试结果：{}", teacherMapper.insertTeacher(teacher));
    }



    @Test
    void updateTest() {
        Teacher teacher = new Teacher();
        teacher.setTid(1);
        teacher.setRemark("计算机视觉方向，欢迎选题");
        logger.info("测试结果：{}", teacherMapper.updateProfile(teacher));
    }

    @Test
    void deleteTest() {
        logger.info("测试结果：{}", teacherMapper.deleteTeacherByTid(3));
    }

}