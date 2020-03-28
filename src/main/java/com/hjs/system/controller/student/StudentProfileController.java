package com.hjs.system.controller.student;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.model.Student;
import com.hjs.system.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/28 20:08
 * @Modified By:
 */

@Controller
public class StudentProfileController {
    private static final Logger logger = LoggerFactory.getLogger(StudentProfileController.class);

    // @Autowired注入的request对象是个代理对象，被spring代理了，每次使用request时，通过代理，
    // 使用的是当前线程对应的request对象, 即当前请求的Request，也可以写到对应的方法作为参数传递
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StudentService studentServiceImpl;


    @RequestMapping(value = "/student/profile/avatarUpload", method = RequestMethod.POST)
    @ResponseBody
    public String studentAvatarUpload(String access_token, MultipartFile file) {
        if (StringUtil.isEmpty(access_token)) {
            return JSONUtil.returnFailResult("前端传递的 access_token 为空，请重新登录!");
        }
        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        if (access_token.equals(sessionId)) {
            logger.info("开始学生头像上传...");
            //获取文件名
            String originalFileName = file.getOriginalFilename();
            logger.info("上传图片文件名: " + originalFileName);
            Student student = (Student) SecurityUtils.getSubject().getPrincipal();
            Integer sid = student.getSid();
//            if ()
//                String realPath = request.getServletContext().getRealPath("/public/student/");
//            File _dirFile = new File(realPath);
//            if ()
            return null;
        } else {
            return JSONUtil.returnFailResult("登录状态已失效，请重新登录!");
        }

    }
}
