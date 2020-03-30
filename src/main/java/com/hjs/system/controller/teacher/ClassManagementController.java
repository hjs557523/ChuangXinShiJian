package com.hjs.system.controller.teacher;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.model.Class;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.ClassService;
import com.hjs.system.service.CourseService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/30 13:14
 * @Modified By:
 */

@Controller
public class ClassManagementController {
    private static final Logger logger = LoggerFactory.getLogger(ClassManagementController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClassService classServiceImpl;

    @Autowired
    private CourseService courseServiceImpl;

    @RequestMapping(value = "/teacher/class/add")
    @ResponseBody
    public String addClass(Class classes) {
        //校验数据
        if (StringUtil.isEmpty(classes.getClassName()))
            return JSONUtil.returnFailResult("请输入自定义的创新实践班级名称");
        else if (StringUtil.isEmpty(classes.getCourseId().toString()))
            return JSONUtil.returnFailResult("请选择该班级所属的创新实践课程");
        else if (!Pattern.compile("^[1-4]$").matcher(classes.getCourseId().toString()).matches())
            return JSONUtil.returnFailResult("根据课程安排, 请选择创新实践1/创新实践2/创新实践3/创新综合实践");
        else {
            Teacher current_user = (Teacher) SecurityUtils.getSubject().getPrincipal();
            Integer tid = current_user.getTid();
            boolean isFinished = false;//创建班级，默认未完结
            classes.setTid(tid);
            classes.setIsFinished(isFinished);

            try {
                if (classServiceImpl.insertClass(classes) > 0) {
                    return JSONUtil.returnSuccessResult("创建成功!");
                } else {
                    return JSONUtil.returnFailResult("创建失败");
                }
            } catch (Exception e) {
                logger.info("创建班级失败: " + e.getMessage());
                return JSONUtil.returnFailResult("创建失败, 请重试");
            }
        }
    }


    @RequestMapping(value = "/teacher/class/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String modifyClassInfo(Class classes) {
        //校验数据
        if (StringUtil.isEmpty(classes.getClassName()))
            return JSONUtil.returnFailResult("请设置该创新实践班级的班级名称!");
        else if (StringUtil.isEmpty(classes.getCourseId().toString()))
            return JSONUtil.returnFailResult("请设置该班级所属的创新实践课程！");
        else if (StringUtil.isEmpty(classes.getIsFinished().toString()))
            return JSONUtil.returnFailResult("请设置该创新实践班级的进行状态！");
        else {
            try {
                if (classServiceImpl.updateClass(classes) > 0) {//NUll值不会修改
                    return JSONUtil.returnSuccessResult("修改成功!");
                } else {
                    return JSONUtil.returnFailResult("修改失败");
                }
            } catch (Exception e) {
                logger.info("修改Class失败, 错误: " + e.getMessage());
                return JSONUtil.returnFailResult("修改失败, 请重试!");

            }
        }
    }

}
