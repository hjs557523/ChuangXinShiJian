package com.hjs.system.controller.teacher;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.controller.student.WeekPaperController;
import com.hjs.system.model.*;
import com.hjs.system.model.Process;
import com.hjs.system.service.*;
import com.hjs.system.vo.WeekPaperVO;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/5/8 12:37
 * @Modified By:
 */

@Controller
public class TeacherWeekPaperController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherWeekPaperController.class);

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

    @Autowired
    private StudentService studentServiceImpl;


    @RequestMapping(value = "/teacher/weekPaper/getWeekPaper", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWeekPaperToTutor(@RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        Integer tid = teacher.getTid();
        List<WeekPaperVO> weekPaperVOList = new ArrayList<>();
        try {
            Page<WeekPaper> weekPapers = weekPaperServiceImpl.findWeekPaperByTidAndPage(tid, pageNum, pageSize);
            PageInfo<WeekPaper> pageInfo = new PageInfo<>(weekPapers);
            Integer count = (int) pageInfo.getTotal();
            for (WeekPaper p : pageInfo.getList()) {
                WeekPaperVO weekPaperVO = new WeekPaperVO();
                weekPaperVO.setWeekPaper(p);
                Integer sid = p.getSid();
                Student student = studentServiceImpl.findStudentBySid(sid);
                weekPaperVO.setStudent(student);
                weekPaperVOList.add(weekPaperVO);
            }
            if (count == 0)
                return JSONUtil.returnEntityResult(count, "未查找到周报信息", weekPaperVOList);
            else
                return JSONUtil.returnEntityResult(count, "周报信息如下", weekPaperVOList);
        } catch (Exception e) {
            logger.info("数据库发生异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");
        }
    }


    @RequestMapping(value = "/teacher/weekPaper/downLoadexFile", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String downLoadexFile(@RequestParam("uuid")String uuid, HttpServletResponse response) throws IOException {
        WeekPaper weekPaper = weekPaperServiceImpl.findWeekPaperByUUID(uuid);
        String fileName = null;
        if (weekPaper == null)
            return JSONUtil.returnFailResult("周报不存在");
        else if (weekPaper.getFile() == null)
            return JSONUtil.returnFailResult("周报附件不存在");
        File file = new File(weekPaper.getFile());
        if (!file.exists()) {
            throw new IOException("文件不存在!");
        }

        // 获取文件名
        StringTokenizer stringTokenizer = new StringTokenizer(weekPaper.getFile(), "/");
        while (stringTokenizer.hasMoreElements()) {
            fileName = stringTokenizer.nextToken();
        }

        // 解决下载后文件名称中文乱码
        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");

        response.reset();
        // 设置强制下载打开
        response.setContentType("application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);


        byte[] buf = new byte[1024];
        OutputStream os = null;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
            os = response.getOutputStream();
            int length = 0;
            while ((length = bis.read(buf)) != -1) {
                os.write(buf, 0, length);
                os.flush();
            }

        } catch (IOException exc) {
            logger.info("文件读写异常: " + exc.getMessage());
            return JSONUtil.returnFailResult("文件下载异常");
        }

        return null;
    }



}
