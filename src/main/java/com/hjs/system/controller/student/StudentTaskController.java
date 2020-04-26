package com.hjs.system.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.hjs.system.api.ApiUtil;
import com.hjs.system.api.GraphQLApiUtils;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.model.Group;
import com.hjs.system.model.Student;
import com.hjs.system.model.Task;
import com.hjs.system.service.GroupService;
import com.hjs.system.service.StudentService;
import com.hjs.system.service.TaskService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/15 17:52
 * @Modified By:
 */

@Controller
public class StudentTaskController {

    private static final Logger logger = LoggerFactory.getLogger(StudentTaskController.class);

    @Autowired
    private TaskService taskServiceImpl;

    @Autowired
    private GroupService groupServiceImpl;

    @Autowired
    private StudentService studentServiceImpl;


    @RequestMapping(value = "/student/task/addTask", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addTask(Task task) {
        logger.info(task.toString());
        if (task == null) {
            return JSONUtil.returnFailResult("任务null");
        }

        // 获取当前学生用户
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        task.setStudent(student);
        task.setFinished(0);
        try {
            Integer groupId = task.getGroupId();
            Group group = groupServiceImpl.findGroupByGid(groupId);
            Integer ownerId = group.getOwnerId();
            Student owner = studentServiceImpl.findStudentBySid(ownerId);

            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Basic " + SecurityUtils.getSubject().getSession().getAttribute("token"));
            header.put("Content-Type", "application/json; charset=utf-8");
            header.put("Connection", "keep-Alive");
            header.put("User-Agent", ((Student) SecurityUtils.getSubject().getPrincipal()).getGithubName());

            logger.info(header.toString());

            String url = String.format(ApiUtil.REPO_ALL_ISSUES_URL, new Object[]{owner.getGithubName(), group.getRepositoryUrl()});

            logger.info(url);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", task.getTaskName());
            String json = jsonObject.toJSONString();
            logger.info(json);
            String result = ApiUtil.ApiPostRequest(url, json, header);
            logger.info(result);
            JSONObject resultJson = JSONObject.parseObject(result);
            if (resultJson.getString("number") == null) {
                return JSONUtil.returnFailResult("添加任务失败1");
            }

            task.setIssueNumber(Integer.parseInt(resultJson.getString("number")));

            if (taskServiceImpl.insertTask(task) > 0) {
                return JSONUtil.returnSuccessResult("添加任务成功");
            } else {
                return JSONUtil.returnFailResult("添加任务失败2");
            }
        } catch (Exception e) {
            logger.info("异常: " + e.getMessage());
            return JSONUtil.returnFailResult("异常");
        }

    }

}
