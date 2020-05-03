package com.hjs.system.controller.student;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.model.Student;
import com.hjs.system.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.regex.Pattern;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/28 20:08
 * @Modified By:
 */

@Controller
public class StudentProfileController {
    private static final Logger logger = LoggerFactory.getLogger(StudentProfileController.class);

    //@Autowired注入的request对象只是个代理对象，被spring代理了，每次使用request时，通过代理，
    //使用的是当前线程对应的request对象, 即当前请求的Request，也可以写到对应的方法作为参数传递
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StudentService studentServiceImpl;


    @RequestMapping(value = "/student/profile")
    @ResponseBody
    public String getStudentProfile() {
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        return JSONUtil.returnEntityResult(student);
    }


    /**
     * 修改头像
     * @param file
     * @return
     */
    @RequestMapping(value = "/student/profile/avatarUpload", method = RequestMethod.POST)
    @ResponseBody
    public String studentAvatarUpload(MultipartFile file) {
        logger.info("开始学生头像上传...");
        //获取文件名
        String originalFileName = file.getOriginalFilename();
        logger.info("上传图片文件名: " + originalFileName);
        Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();
        String picImg = "/public/student/" + current_user.getStudentId() + "/avatar/";
        String realPath = request.getServletContext().getRealPath(picImg);//目录
        File _dirFile = new File(realPath);
        if (!_dirFile.exists() && !_dirFile.isDirectory()) {
            logger.info("文件夹路径不存在，创建文件夹");
            _dirFile.mkdirs();
        }

        //根据时间函数，生成一个新的，不产生重复的文件名
        String uploadFileName = System.currentTimeMillis() + "_" + originalFileName;
        logger.info("获取上传路径: " + realPath + ", 上传的真实文件名: " + uploadFileName);
        boolean flag = true;

        //合并文件
        RandomAccessFile raFile = null;
        BufferedInputStream inputStream = null;

        try {
            File dirFile = new File(realPath, uploadFileName);
            //以读写的方式打开目标文件, 若文件不存在, 则会自动创建
            raFile = new RandomAccessFile(dirFile, "rw");
            raFile.seek(raFile.length());//将文件指针移动到文件最后一个字节的下一个位置
            inputStream = new BufferedInputStream(file.getInputStream());

            byte[] buf = new byte[1024]; //缓存读取到的数据，缓冲数组的长度一般是1024的倍数
            int length = 0;//保存每次读取到的字节个数

            //循环读取文件内容, 输入流中
            while ((length = inputStream.read(buf)) != -1) { //等同于inputStream.read(buf, 0, b.length)
                raFile.write(buf, 0, length);
            }

        } catch (Exception e) {
            flag = false;
            logger.info("上传出错: {}", e.getMessage());
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (raFile != null)
                    raFile.close();
            } catch(Exception e){
                flag = false;
                logger.info("上传出错: {}", e.getMessage());
            }
        }

        if (flag) {
            Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
            student.setPicImg(realPath + uploadFileName);
            //成功->修改学生头像
            if (studentServiceImpl.updateStudent(student) > 0) {//返回的是受影响的行数，而不是匹配到的行数
                //重新存入session
                SecurityUtils.getSubject().getSession().setAttribute("student", student);
                //更新AuthenticationInfo
                Student authenticationInfo = (Student) SecurityUtils.getSubject().getPrincipal();
                authenticationInfo.setPicImg(student.getPicImg());
            }

            return JSONUtil.returnSuccessResult("修改头像成功!");

        } else {
            return JSONUtil.returnFailResult("修改头像失败");
        }

    }



    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @return
     */
    @RequestMapping(value = "/student/profile/passwordModify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String studentPasswordModify(String oldPwd, String newPwd, String confirmPwd) {
        //校验数据
        if (StringUtil.isEmpty(oldPwd))
            return JSONUtil.returnFailResult("原密码不能为空!");
        else if (StringUtil.isEmpty(newPwd))
            return JSONUtil.returnFailResult("新密码不能为空!");
        else if (StringUtil.isEmpty(confirmPwd))
            return JSONUtil.returnFailResult("确认密码不能为空!");
        else if (oldPwd.trim().length() < 5 || oldPwd.trim().length() > 20)
            return JSONUtil.returnFailResult("原密码长度必须在5~20之间!");
        else if (newPwd.trim().length() < 5 || newPwd.trim().length() > 20)
            return JSONUtil.returnFailResult("新密码长度必须在5~20之间!");
        else if (!newPwd.equals(confirmPwd))
            return JSONUtil.returnFailResult("确认密码不一致, 请重新输入!");

        Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();
        //旧密码加密
        String oldPwdMd5 = new Md5Hash(oldPwd, current_user.getStudentId(), 2).toHex();
        //比对原密码是否正确
        if (!oldPwdMd5.equals(current_user.getPassword()))
            return JSONUtil.returnFailResult("原密码错误!");
        else {
            //对新密码加密
            String newPwdMd5 = new Md5Hash(newPwd, current_user.getStudentId(), 2).toHex();
            try {
                if (newPwdMd5.equals(current_user.getPassword()))
                    return JSONUtil.returnFailResult("新密码不能与原密码一致!");
                else if (studentServiceImpl.updatePassword(current_user.getSid(), newPwdMd5) > 0) {
                    //登出
                    SecurityUtils.getSubject().logout();
                    return JSONUtil.returnSuccessResult("修改密码成功, 请重新登录!");
                } else {
                    return JSONUtil.returnFailResult("修改密码失败!");
                }
            } catch (Exception e) {
                logger.info("修改密码失败: " + e.getMessage());
                return JSONUtil.returnFailResult("修改密码失败, 请重试!");
            }
        }
    }


    /**
     * 修改学生个人信息
     * @param student
     * @return
     */
    @RequestMapping(value = "/student/profile/profileModify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String studentProfileModify(Student student) {
        logger.info("修改学生信息", student);
        //校验提交的student
        if (StringUtil.isEmpty(student.getStudentId()))
            return JSONUtil.returnFailResult("学生学号不能为空!");
        else if (!Pattern.compile("^\\d{8}$").matcher(student.getStudentId()).matches())
            return JSONUtil.returnFailResult("请输入8位学号!");
        else if (StringUtil.isEmpty(student.getName()))
            return JSONUtil.returnFailResult("学生姓名不能为空!");
        else if (!Pattern.compile("[\\u4E00-\\u9FFF]+").matcher(student.getName()).matches())//unicode编码正则匹配简体汉字，[\\u4E00-\\u9FA5]可匹配繁体
            return JSONUtil.returnFailResult("学生姓名必须为中文!");
        else if (!StringUtil.isEmpty(student.getMobile()) && !Pattern.compile("^1[34578]\\d{9}$").matcher(student.getMobile()).matches())//手机号码可以为空
            return JSONUtil.returnFailResult("手机号码不符合规范!");
        else if (!StringUtil.isEmpty(student.getEmail()) && !Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+").matcher(student.getEmail()).matches())//邮箱可以为空
            return JSONUtil.returnFailResult("邮箱填写不符合规范!");
        else if (StringUtil.isEmpty(student.getGithubName()))
            return JSONUtil.returnFailResult("github账号名不能为空!");

        try {
            if (studentServiceImpl.updateStudent(student) > 0) {
                //更新当前用户的角色信息 AuthenticationInfo
                Student authenticationInfo = (Student) SecurityUtils.getSubject().getPrincipal();
                authenticationInfo.setStudentId(student.getStudentId());
                authenticationInfo.setName(student.getName());
                authenticationInfo.setEmail(student.getEmail());
                authenticationInfo.setMobile(student.getMobile());
                authenticationInfo.setGithubName(student.getGithubName());

                //更新session
                SecurityUtils.getSubject().getSession().setAttribute("student", authenticationInfo);
                return JSONUtil.returnSuccessResult("修改成功!");
            } else {
                return JSONUtil.returnFailResult("修改失败!");
            }

        } catch (Exception e) {
            logger.info("修改时发生错误: " + e.getMessage());
            return JSONUtil.returnFailResult("修改失败, 请重试!");
        }
    }



}
