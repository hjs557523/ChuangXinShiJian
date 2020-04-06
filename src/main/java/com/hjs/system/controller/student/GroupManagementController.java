package com.hjs.system.controller.student;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.model.Group;
import com.hjs.system.model.GroupMember;
import com.hjs.system.model.Student;
import com.hjs.system.service.GroupMemberService;
import com.hjs.system.service.GroupService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 创建小组
     * @param group
     * @param subjectId
     * @return
     */
    @RequestMapping(value = "/student/group/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String createGroup(@RequestBody Group group, @RequestBody Integer subjectId) {

        // 获取当前用户
        Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();
        Integer ownerId = current_user.getSid();

        group.setStateId(0);// 默认初始创建为 “未完结” 状态
        group.setSubjectId(subjectId);
        group.setOwnerId(ownerId);


        // 添加当前用户到组员关系表中
        try {
            if (groupServiceImpl.createGroup(group) > 0)
                return JSONUtil.returnSuccessResult("创建成功!");
            else
                return JSONUtil.returnFailResult("创建失败, 请稍后重试!");

        } catch (Exception e) {
            logger.info("数据库发生异常: {}" + e.getMessage());
            return JSONUtil.returnFailResult("创建失败, 数据库发生异常!");
        }

    }




    /**
     * 加入小组
     * @param gid
     * @return
     */
    @RequestMapping(value = "/student/group/join", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String joinGroup(@RequestParam("gid") Integer gid) {

        // 获取当前用户
        Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();

        GroupMember groupMember = new GroupMember();
        groupMember.setStudent(current_user);
        groupMember.setGroupId(gid);

        try {
            if (groupMemberServiceImpl.insertGroupMember(groupMember) > 0)
                return JSONUtil.returnSuccessResult("加入成功");
            else
                return JSONUtil.returnFailResult("加入失败");

        } catch (Exception e) {
            logger.info("数据库异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常, 请稍后重试!");
        }
    }




}
