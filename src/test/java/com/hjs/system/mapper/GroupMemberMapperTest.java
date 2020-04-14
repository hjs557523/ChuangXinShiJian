package com.hjs.system.mapper;

import com.github.pagehelper.PageHelper;
import com.hjs.system.SystemApplication;
import com.hjs.system.model.GroupMember;
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
 * @date Created in 2020/4/14 14:24
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GroupMemberMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(ClassMemberMapperTest.class);

    @Autowired
    private GroupMemberMapper groupMemberMapper;


    @Test
    public void Test1() {
        PageHelper.startPage(1, 5);
        for (GroupMember groupMember : groupMemberMapper.findGroupMemberByStudentId(1)) {
            logger.info(groupMember.toString());
        }
    }
}