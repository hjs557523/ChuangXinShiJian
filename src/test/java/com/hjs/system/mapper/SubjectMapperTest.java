package com.hjs.system.mapper;

import com.hjs.system.SystemApplication;
import com.hjs.system.model.Subject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author 黄继升 16041321
 * @Description: 测试类
 * @date Created in 2020/4/6 19:30
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
class SubjectMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(SubjectMapperTest.class);

    @Autowired
    private SubjectMapper subjectMapper;


    @Test
    public void test() {
        for (Subject s : subjectMapper.findSubjectByTname("福"))
            logger.info(s.toString());
    }
}