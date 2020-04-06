package com.hjs.system.controller.student;

import com.hjs.system.service.GroupMemberService;
import com.hjs.system.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/6 14:38
 * @Modified By:
 */

@Controller("StudentGroupManagement")
public class GroupManagementController {

    private static final Logger logger = LoggerFactory.getLogger(GroupManagementController.class);

    @Autowired
    private GroupService groupServiceImpl;

    @Autowired
    private GroupMemberService groupMemberServiceImpl;



}
