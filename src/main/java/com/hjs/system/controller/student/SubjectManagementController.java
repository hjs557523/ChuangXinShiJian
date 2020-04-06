package com.hjs.system.controller.student;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.base.utils.JSONUtil;
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

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/6 15:13
 * @Modified By:
 */

@Controller("StudentSubjectManagement")
public class SubjectManagementController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectManagementController.class);

    @Autowired
    private SubjectService subjectServiceImpl;


    @RequestMapping(value = "/student/subject/findAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllSubjectByPage(@RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        if (pageNum == null)
            pageNum = 1;
        if (pageSize == null)
            pageSize = 12;

        logger.info("分页查询第{}页，每页{}条", pageNum, pageSize);

        try {
            Page<Subject> subjects = subjectServiceImpl.findSubjectByPage(pageNum, pageSize);
            PageInfo<Subject> pageInfo = new PageInfo<>(subjects);
            Integer count = (int) pageInfo.getTotal();
            if (count == 0)
                return JSONUtil.returnEntityResult(count, "未查找到课题记录", pageInfo);
            else
                return JSONUtil.returnEntityResult(count, "所有老师的课题记录如下", pageInfo);
        } catch (Exception e) {
            logger.info("查询出错：" + e.getMessage());
            return JSONUtil.returnFailResult("数据库查询失败");
        }
    }


    public String findSubjectByTname(@RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize, @RequestParam("name") String name) {

    }
}
