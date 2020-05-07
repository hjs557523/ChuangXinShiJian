package com.hjs.system.controller.student;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.model.Group;
import com.hjs.system.model.GroupMember;
import com.hjs.system.model.Student;
import com.hjs.system.service.GroupMemberService;
import com.hjs.system.service.GroupService;
import com.hjs.system.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    private HttpServletRequest request;

    @Autowired
    private GroupService groupServiceImpl;

    @Autowired
    private GroupMemberService groupMemberServiceImpl;

    @Autowired
    private StudentService studentServiceImpl;


    /**
     * 创建小组
     * @param group
     * @param
     * @return
     */
    @RequestMapping(value = "/student/group/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String createGroup(@RequestBody Group group) {

        System.out.println(group);

        // 获取当前用户
        Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();
        Integer ownerId = current_user.getSid();

        group.setStateId(0);// 默认初始创建为 “未完结” 状态
        group.setOwnerId(ownerId);
        group.setOauthToken(null);



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


//    /**
//     * 删除小组
//     * @param groupId
//     * @return
//     */
//    @RequestMapping(value = "/student/group/delete", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String createGroup(@RequestParam("groupId") Integer groupId) {
//        if (groupId == null)
//            return JSONUtil.returnFailResult("groupId null");
//
//        try {
//            if ()
//        }
//        return null;
//    }




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
        Group group = groupServiceImpl.findGroupByGid(gid);
        GroupMember groupMember = new GroupMember();
        groupMember.setStudent(current_user);
        groupMember.setGroup(group);

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


    /**
     * 查询所加入的小组
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/student/group/findAllJoin", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllJoinGroupBySid(@RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        if (pageNum == null)
            pageNum = 1;
        if (pageSize == null)
            pageSize = 12;

        logger.info("分页查询第{}页，每页{}条", pageNum, pageSize);

        try {

            // 获取当前用户
            Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();
            Integer sid = current_user.getSid();
            Page<GroupMember> allMyGroups = groupMemberServiceImpl.findGroupMemberByStudentId(current_user.getSid(), pageNum, pageSize);
            PageInfo<GroupMember> pageInfo = new PageInfo<>(allMyGroups);
            Integer count = (int) pageInfo.getTotal();

            if (count == 0)
                return JSONUtil.returnEntityResult(count, "未查找到加入小组信息", pageInfo);
            else
                return JSONUtil.returnEntityResult(count, "加入小组信息如下", pageInfo);

        } catch (Exception e) {
            logger.info("查询出错：" + e.getMessage());
            return JSONUtil.returnFailResult("数据库查询失败");

        }
    }


    /**
     * 查找组长信息
     * @param ownerIdList
     * @return
     */
    @RequestMapping(value = "/student/group/findOwner", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findGroupOwner(@RequestBody List<Integer> ownerIdList) {
        List<Student> list = new ArrayList<>();

        if (StringUtil.isEmpty(ownerIdList.toString())) {
            return JSONUtil.returnFailResult("组长Id列表为空!");
        }

        try {
            for (Integer ownerId : ownerIdList) {
                Student student = studentServiceImpl.findStudentBySid(ownerId);
                if (student != null) {
                    list.add(student);
                }
                else {
                    return JSONUtil.returnFailResult("数据库异常");
                }
            }
            return JSONUtil.returnEntityResult(list);

        } catch (Exception e) {
            logger.info("数据库查询异常 :" + e.getMessage());
            return JSONUtil.returnFailResult("查询失败");
        }

    }


    /**
     * 查找小组所有成员
     * @param groupId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/student/group/findAllMember",  method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllMemberByGroupId(@RequestParam("groupId") Integer groupId, @RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        if (groupId == null)
            return JSONUtil.returnFailResult("小组id null");
        if (pageNum == null)
            pageNum = 1;
        if (pageSize == null)
            pageSize = 12;

        try {
            Page<GroupMember> groupMembers = groupMemberServiceImpl.findGroupMemberByGroupId(groupId, pageNum, pageSize);
            PageInfo<GroupMember> pageInfo = new PageInfo<>(groupMembers);
            Integer count = (int) pageInfo.getTotal();
            if (count == 0)
                return JSONUtil.returnEntityResult(count, "当前小组没有成员加入", pageInfo);
            else
                return JSONUtil.returnEntityResult(count, "小组成员如下", pageInfo);

        } catch (Exception e) {
            logger.info("数据库异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");
        }

    }




}
