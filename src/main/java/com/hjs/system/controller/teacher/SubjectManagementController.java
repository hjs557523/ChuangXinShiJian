package com.hjs.system.controller.teacher;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.model.Subject;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.SubjectService;
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
import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description: 课题管理功能
 * @date Created in 2020/4/4 19:27
 * @Modified By:
 */

@Controller("TeacherSubjectManagement")
public class SubjectManagementController {

    private static final Logger logger = LoggerFactory.getLogger(ClassManagementController.class);

    @Autowired
    private SubjectService subjectServiceImpl;


    /**
     * 教师创建课题接口
     * @param subject
     * @return
     */
    @RequestMapping(value = "/teacher/subject/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String createSubject(Subject subject) {
        if (StringUtil.isEmpty(subject.getSubjectName()))
            return JSONUtil.returnFailResult("请填写课题名称!");
        else if (StringUtil.isEmpty(subject.getSubjectDetail()))
            return JSONUtil.returnFailResult("请描述课题要求!");
        else if (StringUtil.isEmpty(subject.getRemark()))
            subject.setRemark("暂无备注");

        // 获取当前教师用户
        Teacher current_user = (Teacher) SecurityUtils.getSubject().getPrincipal();
        Integer tid = current_user.getTid();
        subject.setTid(tid);
        try {
            if (subjectServiceImpl.insertSubject(subject) > 0)
                return JSONUtil.returnSuccessResult("创建课题成功!");
            else
                return JSONUtil.returnFailResult("创建课题失败, 请稍后重试!");
        } catch (Exception e) {
            logger.info("创建课题失败: " + e.getMessage());
            return JSONUtil.returnFailResult("创建课题失败, 数据库异常！");
        }
    }




    /**
     * 教师删除课题接口
     * @return
     */
    @RequestMapping(value = "/teacher/subject/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteSubject(Integer subjectId) {
        if (subjectId == null)
            return JSONUtil.returnFailResult("请选择要删除的课题");
        else {
            try {
                // 获取当前教师用户
                Teacher current_user = (Teacher) SecurityUtils.getSubject().getPrincipal();
                Subject delSubejct = subjectServiceImpl.findSubjectBySubjectId(subjectId);
                // 删除前再检查是否是当前老师的课题
                if (delSubejct.getTid() != current_user.getTid())
                    return JSONUtil.returnFailResult("您没有权限删除该课题！");
                else if (subjectServiceImpl.deleteSubjectBySubjectId(subjectId) > 0)
                    return JSONUtil.returnSuccessResult("删除课题成功！");
                else
                    return JSONUtil.returnFailResult("删除课题失败，请稍后重试！");
            } catch (Exception e) {
                logger.info("删除课题出错: " + e.getMessage());
                return JSONUtil.returnFailResult("删除课题出错，数据库异常！");
            }
        }
    }



    /**
     * 教师查看所创建的课题接口
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/teacher/subject/findAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findMySubjectByPage(@RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        } else if (pageSize == null) {
            pageSize = 12;
        }

        logger.info("分页查询第{}页，每页{}条", pageNum, pageSize);
        // 获取当前教师用户
        Teacher current_user = (Teacher) SecurityUtils.getSubject().getPrincipal();
        Integer tid = current_user.getTid();
        try {
            Page<Subject> subjects = subjectServiceImpl.findSubjectByTid(tid, pageNum, pageSize);
            PageInfo<Subject> pageInfo = new PageInfo<>(subjects);
            Integer count = (int) pageInfo.getTotal();
            if (count == 0)
                return JSONUtil.returnEntityResult(count, "您没有任何课题，请先创建课题！", pageInfo);
            else
                return JSONUtil.returnEntityResult(count, "您创建的课题记录如下：", pageInfo);
        } catch (Exception e) {
            logger.info("查询出错：" + e.getMessage());
            return JSONUtil.returnFailResult("数据库查询失败");
        }
    }


    /**
     * 教师修改课题接口
     * @param subject
     * @return
     */
    @RequestMapping(value = "/teacher/subject/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String modifySubjectInfo(Subject subject) {
        // 校验数据
        if (StringUtil.isEmpty(subject.getSubjectName()))
            return JSONUtil.returnFailResult("请设置您的课题名称");
        else if (StringUtil.isEmpty(subject.getSubjectDetail()))
            return JSONUtil.returnFailResult("请设置您的课题内容");
        else if (StringUtil.isEmpty(subject.getRemark()))
            subject.setRemark("暂无备注");
        try {
            if (subjectServiceImpl.updateSubject(subject) > 0) {
                return JSONUtil.returnSuccessResult("修改成功！");
            } else {
                return JSONUtil.returnFailResult("修改失败，请稍后重试！");
            }

        } catch (Exception e) {
            logger.info("修改Subject失败, 错误: " + e.getMessage());
            return JSONUtil.returnFailResult("修改失败，数据库发生异常！");
        }
    }



//    @RequestMapping(value = "/teacher/subject/batchDel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String batchDelete(@RequestParam(value = "subjectIdList[]") List<Integer> classIdList) {
//        try {
//
//        }
//        return null;
//    }
}
