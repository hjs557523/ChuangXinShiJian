package com.hjs.system.service.impl;

import com.hjs.system.SystemApplication;
import com.hjs.system.model.Group;
import com.hjs.system.service.GroupService;
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
 * @date Created in 2020/4/15 0:45
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GroupServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImplTest.class);

    @Autowired
    private GroupService groupServiceImpl;

    @Test
    void createGroup() {
        Group group = new Group();
        group.setGroupName("文本情感分析工具开发小组");
        group.setOwnerId(3);
        group.setSubjectId(6);
        group.setRepositoryUrl("https://github.com/hjs557523/cxsj3");
        groupServiceImpl.createGroup(group);
    }
}