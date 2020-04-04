package com.hjs.system.controller.teacher;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.model.Class;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.ClassService;
import com.hjs.system.service.CourseService;
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
import java.util.regex.Pattern;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/30 13:14
 * @Modified By:
 */

@Controller("TeacherClassManagement")
public class ClassManagementController {

    private static final Logger logger = LoggerFactory.getLogger(ClassManagementController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClassService classServiceImpl;

    @Autowired
    private CourseService courseServiceImpl;


    /**
     * 教师创建班级接口
     * @param classes
     * @return
     */
    @RequestMapping(value = "/teacher/class/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String createClass(Class classes) {
        //校验数据
        if (StringUtil.isEmpty(classes.getClassName()))
            return JSONUtil.returnFailResult("请输入自定义的创新实践班级名称");
        else if (classes.getCourse().getCid() == null)
            return JSONUtil.returnFailResult("请选择该班级所属的创新实践课程");
        else if (!Pattern.compile("^[1-4]$").matcher(classes.getCourse().getCid().toString()).matches())
            return JSONUtil.returnFailResult("根据课程安排, 请选择创新实践1/创新实践2/创新实践3/创新综合实践");
        else {
            // 获取当前教师用户
            Teacher current_user = (Teacher) SecurityUtils.getSubject().getPrincipal();
            Integer tid = current_user.getTid();
            classes.getTeacher().setTid(tid);

            boolean isFinished = false;//初始创建班级，因此默认未完结
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


    /**
     * 教师更新班级信息接口
     * @param classes
     * @return
     */
    @RequestMapping(value = "/teacher/class/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String modifyClassInfo(Class classes) {
        //校验数据
        if (StringUtil.isEmpty(classes.getClassName()))
            return JSONUtil.returnFailResult("请必须设置您的创新实践班级名称!");
        else if (StringUtil.isEmpty(classes.getCourse().getCid().toString()))
            return JSONUtil.returnFailResult("请选择该班级对应的创新实践课程类型!");
        else if (StringUtil.isEmpty(classes.getIsFinished().toString()))
            return JSONUtil.returnFailResult("请设置该创新实践班级的进行状态！");
        else {
            try {
                if (classServiceImpl.updateClass(classes) > 0) {//提交的对象的NUll值不会被提交更改
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


    /**
     * 教师删除班级接口
     * @param Cid
     * @return
     */
    @RequestMapping(value = "/teacher/class/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteClass(Integer Cid) {
        try {
            if (classServiceImpl.deleteClassByCid(Cid) > 0) {
                //需要删除对应所有小组的信息？以及班级成员信息表和小组信息表？
                return JSONUtil.returnSuccessResult("删除成功!");
            } else {
                return JSONUtil.returnFailResult("删除失败");
            }
        } catch (Exception e) {
            logger.info("删除Class失败, 错误: " + e.getMessage());
            return JSONUtil.returnFailResult("删除失败, 请重试!");
        }
    }




    /**
     * 教师查看其所创建的所有班级
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/teacher/class/findAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8" )
    @ResponseBody
    public String findMyClassesByPage(@RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        } else if (pageSize == null) {
            pageSize = 12;
        }
        logger.info("分页查询第{}页，每页{}条", pageNum, pageSize);

        Teacher current_user = (Teacher) SecurityUtils.getSubject().getPrincipal();
        Integer tid = current_user.getTid();
        Page<Class> classes = classServiceImpl.findClassByTid(tid, pageNum, pageSize);
        // 需要把Page包装成PageInfo对象才能序列化，该插件也默认实现了一个PageInfo
        PageInfo<Class> pageInfo = new PageInfo<Class>(classes);

        Integer count = (int) pageInfo.getTotal();
        if (count == 0)
            return JSONUtil.returnEntityResult(count, "您还没有任何班级数据, 请先创建班级!", pageInfo);
        else {
            return JSONUtil.returnEntityResult(count, "成功返回您的班级数据!", pageInfo);
        }

    }



    /**
     * 教师批量删除创建的所有班级接口
     * @param classIdList
     * @return
     */
    //前端给后端传JSON数组: https://www.cnblogs.com/yfzhou/p/9661994.html
    @RequestMapping(value = "/teacher/class/batchDel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchDelete(@RequestParam(value = "classIdList[]") List<Integer> classIdList) {
        try {
            for (Integer cid : classIdList) {
                if (classServiceImpl.deleteClassByCid(cid) < 0)
                    return JSONUtil.returnFailResult("记录id = " + cid + "删除失败!");
            }
            return JSONUtil.returnSuccessResult("删除成功!");
        } catch (Exception e) {
            logger.info("删除Class失败: " + e.getMessage());
            return JSONUtil.returnFailResult("删除失败, 请重试!");
        }

    }



}
