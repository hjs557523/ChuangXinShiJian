package com.hjs.system.controller.teacher;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/27 22:47
 * @Modified By:
 */

@Controller
public class TeacherIndexController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherIndexController.class);

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private BbsPostService bbsPostService;

    @Autowired
    private BbsReplyService bbsReplyService;


    @RequestMapping(value = "/teacher/index", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTeacherUserInfo(String access_token) {
        //if (access_token == null)
        if (StringUtil.isEmpty(access_token))
            return JSONUtil.returnFailResult("前端传递的 access_token 为空，请重新登录!");

        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();

        if (access_token.equals(sessionId)) {
            //同一会话
            Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
            //统计下老师所管辖的班级人数
            //统计下老师所有的学生人数
            //统计下老师的课题
            //获得公告
            //

            return JSONUtil.returnEntityResult(teacher);

        } else {
            return JSONUtil.returnFailResult("登录状态已失效，请重新登录!");
        }
    }
}
