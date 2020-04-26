package com.hjs.system.controller.student;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.model.Subject;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.SubjectService;
import net.sf.json.JSONNull;
import org.apache.poi.ss.extractor.ExcelExtractor;
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


    /**
     * 学生查看系统所有课题
     * @param pageNum
     * @param pageSize
     * @return
     */
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



    /**
     * 学生根据教师名模糊搜索课题
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @RequestMapping(value = "/student/subject/findByTname", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findSubjectByTname(@RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize, @RequestParam("name") String name) {
        if (pageNum == null)
            pageNum = 1;
        if (pageSize == null)
            pageSize = 12;

        logger.info("分页查询第{}页，每页{}条, 模糊搜索名:{}", new Object[]{pageNum, pageSize, name});

        try {
            Page<Subject> subjects = subjectServiceImpl.findSubjectByTname(pageNum, pageSize, name);
            PageInfo<Subject> pageInfo = new PageInfo<>(subjects);
            Integer count = (int) pageInfo.getTotal();
            if (count == 0)
                return JSONUtil.returnEntityResult(count, "未查找到课题记录", pageInfo);
            else
                return JSONUtil.returnEntityResult(count, "该老师的课题记录如下", pageInfo);

        } catch (Exception e) {
            logger.info("查询出错：" + e.getMessage());
            return JSONUtil.returnFailResult("数据库查询失败");
        }
        
    }


    @RequestMapping(value = "/student/subject/getTeacherInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTeacherInfoBySubjectId(@RequestParam("subjectId") Integer subjectId) {
        if (subjectId == null) {
            return JSONUtil.returnFailResult("课题Id为空");
        }

        try {
            Subject subject = subjectServiceImpl.findSubjectBySubjectId(subjectId);
            if (subject == null) {
                return JSONUtil.returnFailResult("教师信息为空");
            } else {
                Teacher teacher = subject.getTeacher();
                return JSONUtil.returnEntityResult(teacher);
            }
        } catch (Exception e) {
            logger.info("数据库异常" + e.getMessage());
            return JSONUtil.returnFailResult("数据库的信息为空");
        }
    }
}
