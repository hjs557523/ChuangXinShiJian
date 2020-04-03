//package com.hjs.system.controller.student;
//
//import com.hjs.system.base.utils.JSONUtil;
//import com.hjs.system.base.utils.StringUtil;
//import com.hjs.system.model.ClassMember;
//import com.hjs.system.model.Student;
//import com.hjs.system.service.ClassMemberService;
//import org.apache.shiro.SecurityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @author 黄继升 16041321
// * @Description:
// * @date Created in 2020/4/2 20:18
// * @Modified By:
// */
//
//@Controller
//public class ClassManagementController {
//
//    private static final Logger logger = LoggerFactory.getLogger(ClassManagementController.class);
//
//    @Autowired
//    ClassMemberService classMemberService;
//
//    @RequestMapping(value = "/student/class/join", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String joinClass(Integer cid) {
//        Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();
//        if (StringUtil.isEmpty(cid.toString()))
//            return JSONUtil.returnFailResult("请选择加入的班级!");
//        ClassMember classMember = new ClassMember();
//        classMember.setClassId(cid);
//        classMember.setStudentId(current_user.getClassId());
//        classMember.setAccept(false);//等待老师确认
//        try {
//            if (classMemberService.insertClassMember(classMember) > 0) {
//                logger.info("");
//                return JSONUtil.returnSuccessResult("申请加入班级成功! 请等待老师确认");
//            }
//        } catch (Exception e) {
//            logger
//        }
//        return null;
//    }
//}
