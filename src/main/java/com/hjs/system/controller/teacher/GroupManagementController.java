package com.hjs.system.controller.teacher;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.model.Group;
import com.hjs.system.model.GroupMember;
import com.hjs.system.model.Student;
import com.hjs.system.service.GroupService;
import com.hjs.system.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/30 13:15
 * @Modified By:
 */

@Controller("TeacherGroupManagement")
public class GroupManagementController {

    private static final Logger logger = LoggerFactory.getLogger(com.hjs.system.controller.student.GroupManagementController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private GroupService groupServiceImpl;

    @Autowired
    private TeacherService teacherServiceImpl;


    /**
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
//    @RequestMapping(value = "/teacher/group/getAllMyGroup", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String findAllMyGroupBySid(@RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
//        if (pageNum == null)
//            pageNum = 1;
//        if (pageSize == null)
//            pageSize = 12;
//
//        logger.info("分页查询第{}页，每页{}条", pageNum, pageSize);
//
//        try {
//
//            // 获取当前用户
//            Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();
//            Integer sid = current_user.getSid();
//            Page<GroupMember> allMyGroups = groupMemberServiceImpl.findGroupMemberByStudentId(current_user.getSid(), pageNum, pageSize);
//            PageInfo<GroupMember> pageInfo = new PageInfo<>(allMyGroups);
//            Integer count = (int) pageInfo.getTotal();
//
//            if (count == 0)
//                return JSONUtil.returnEntityResult(count, "未查找到加入小组信息", pageInfo);
//            else
//                return JSONUtil.returnEntityResult(count, "加入小组信息如下", pageInfo);
//
//        } catch (Exception e) {
//            logger.info("查询出错：" + e.getMessage());
//            return JSONUtil.returnFailResult("数据库查询失败");
//
//        }
//
//
//    }

    @RequestMapping(value = "/teacher/Group/findSubjectGroup", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllMyGroupBySid(@RequestParam("subjectId")Integer subjectId, @RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        if (pageNum == null)
            pageNum = 1;
        if (pageSize == null)
            pageSize = 12;

        logger.info("分页查询第{}页，每页{}条", pageNum, pageSize);

        try {
            Page<Group> allMyGroups = groupServiceImpl.findGroupBySubjectId(subjectId, pageNum, pageSize);
            PageInfo<Group> pageInfo = new PageInfo<>(allMyGroups);
            Integer count = (int) pageInfo.getTotal();

            if (count == 0)
                return JSONUtil.returnEntityResult(count, "未查找到小组", pageInfo);
            else
                return JSONUtil.returnEntityResult(count, "小组列表如下", pageInfo);

        } catch (Exception e) {
            logger.info("查询出错：" + e.getMessage());
            return JSONUtil.returnFailResult("数据库查询失败");

        }


    }

}
