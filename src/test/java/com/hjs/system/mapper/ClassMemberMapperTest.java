package com.hjs.system.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjs.system.SystemApplication;
import com.hjs.system.model.ClassMember;
import com.hjs.system.model.Student;
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
 * @date Created in 2020/4/6 10:00
 * @Modified By:
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
class ClassMemberMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(ClassMemberMapperTest.class);

    @Autowired
    private ClassMemberMapper classMemberMapperImpl;

    @Test
    public void Test1() {
        PageHelper.startPage(1, 5);
        for (ClassMember classMember : classMemberMapperImpl.findAllClassMember())
        logger.info(classMember.toString());
    }

    @Test
    public void Test2() {
        ClassMember classMember = new ClassMember();
        Student student = new Student();
        classMember.setStudent(student);

        classMemberMapperImpl.insertClassMember(classMember);
    }


    @Test
    public void Test3() {
        ClassMember classMember = new ClassMember();
        classMember.setClassMemberId(4);
        Student student = new Student();
        classMember.setStudent(student);
        classMember.setAccept(true);
        classMemberMapperImpl.updateClassMember(classMember);
    }
}