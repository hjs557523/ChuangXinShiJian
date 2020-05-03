package com.hjs.system.controller.student;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.model.*;
import com.hjs.system.service.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/30 19:54
 * @Modified By:
 */
@Controller
public class WeekPaperController {

    private static final Logger logger = LoggerFactory.getLogger(WeekPaperController.class);

    @Value("${uploadFile.location}")
    private String uploadFileLocation;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private WeekPaperService weekPaperServiceImpl;

    @Autowired
    private GroupMemberService groupMemberServiceImpl;

    @Autowired
    private GroupService groupServiceImpl;

    @Autowired
    private SubjectService subjectServiceImpl;

    @Autowired
    private TeacherService teacherServiceImpl;


    @RequestMapping(value = "/student/weekPaper/addWeekPaper", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllTaskByGroupId(@RequestParam("file") MultipartFile file,
                                       @RequestParam("thisWeekContent") String thisWeekContent,
                                       @RequestParam("nextWeekContent") String nextWeekContent,
                                       @RequestParam("tid") Integer teacherId,
                                       @RequestParam("fileName") String fileName)
    {
        Student current_user = (Student) SecurityUtils.getSubject().getPrincipal();
        String relativePath = uploadFileLocation + "/student/" + current_user.getStudentId() + "/weekPaper/";
        //String realPath = request.getServletContext().getRealPath(relativePath);//目录
        File _dirFile = new File(relativePath);
        if (!_dirFile.exists() && !_dirFile.isDirectory()) {
            logger.info("文件夹路径不存在，创建文件夹");
            if (_dirFile.mkdirs())
                logger.info("创建成功: " + relativePath);
            else
                logger.info("创建失败");
        }

        //根据时间函数，生成一个新的，不产生重复的文件名
        //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        //Date date = new Date(System.currentTimeMillis());
        String uploadFileName = System.currentTimeMillis() + "_" + fileName;
        logger.info("获取上传路径: " + relativePath + ", 上传的真实文件名: " + uploadFileName);
        boolean flag = true;

        //合并文件
        RandomAccessFile raFile = null;
        BufferedInputStream inputStream = null;

        try {
            File dirFile = new File(relativePath, uploadFileName);
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
            WeekPaper weekPaper = new WeekPaper();
            weekPaper.setCreateTime(new Date());
            weekPaper.setFile(relativePath + uploadFileName);
            weekPaper.setThisWeekWork(thisWeekContent);
            weekPaper.setNextWeekWork(nextWeekContent);
            weekPaper.setTid(teacherId);
            weekPaper.setSid(((Student) SecurityUtils.getSubject().getPrincipal()).getSid());
            weekPaper.setUuid(UUID.randomUUID().toString());
            try {
                if (weekPaperServiceImpl.addWeekPaper(weekPaper) > 0) {//返回的是受影响的行数，而不是匹配到的行数
                    //重新存入session
                    return JSONUtil.returnSuccessResult("上传成功");
                } else {
                    return JSONUtil.returnFailResult("上传失败1");
                }
            } catch (Exception e) {
                logger.info("数据库异常: " + e.getMessage());
                return JSONUtil.returnFailResult("上传失败2");
            }
        } else {
            return JSONUtil.returnFailResult("上传失败3");
        }

    }


    @RequestMapping(value = "/student/weekPaper/addWeekPaper2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllTaskByGroupId(@RequestParam("thisWeekContent") String thisWeekContent,
                                       @RequestParam("nextWeekContent") String nextWeekContent,
                                       @RequestParam("tid") Integer teacherId) {
        WeekPaper weekPaper = new WeekPaper();
        weekPaper.setCreateTime(new Date());
        weekPaper.setFile(null);
        weekPaper.setThisWeekWork(thisWeekContent);
        weekPaper.setNextWeekWork(nextWeekContent);
        weekPaper.setTid(teacherId);
        weekPaper.setSid(((Student) SecurityUtils.getSubject().getPrincipal()).getSid());
        weekPaper.setUuid(UUID.randomUUID().toString());
        try {
            if (weekPaperServiceImpl.addWeekPaper(weekPaper) > 0) {//返回的是受影响的行数，而不是匹配到的行数
                //重新存入session
                return JSONUtil.returnSuccessResult("上传成功");
            } else {
                return JSONUtil.returnFailResult("上传失败1");
            }
        } catch (Exception e) {
            logger.info("数据库异常: " + e.getMessage());
            return JSONUtil.returnFailResult("上传失败2");
        }

    }


    @RequestMapping(value = "/student/weekPaper/getMyTutor", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getMyTutor() {
        List<Teacher> list = new ArrayList<>();
        Map<Integer, Teacher> map = new HashMap<>();// 保证教师实体不重复
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Integer sid = student.getSid();
        Page<GroupMember> groupMembers = groupMemberServiceImpl.findGroupMemberByStudentId(sid, 0, 0);
        PageInfo<GroupMember> pageInfo = new PageInfo<>(groupMembers);
        for (GroupMember groupMember : pageInfo.getList()) {
            Integer subjectId = groupMember.getGroup().getSubjectId();
            Teacher teacher = subjectServiceImpl.findSubjectBySubjectId(subjectId).getTeacher();
            map.put(teacher.getTid(), teacher);
        }
        for (Integer tid : map.keySet()) {
            list.add(map.get(tid));
        }

        return JSONUtil.returnEntityResult(list);
    }


}
