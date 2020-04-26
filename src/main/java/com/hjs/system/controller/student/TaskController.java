package com.hjs.system.controller.student;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.api.ApiUtil;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.model.*;
import com.hjs.system.service.GroupService;
import com.hjs.system.service.StudentService;
import com.hjs.system.service.TaskService;
import com.hjs.system.vo.GitHubUserComment;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/15 20:13
 * @Modified By:
 */

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(GroupManagementController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TaskService taskServiceImpl;

    @Autowired
    private GroupService groupServiceImpl;

    @Autowired
    private StudentService studentServiceImpl;

    @RequestMapping(value = "/student/task/findAllTask", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllTaskByGroupId(@RequestParam("groupId") Integer gid, @RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        logger.info(gid.toString());

        if (gid == null)
            return JSONUtil.returnFailResult("groupId is null");

        try {
            Page<Task> tasks = taskServiceImpl.findAllTaskByGroupIdAndPage(gid, pageNum, pageSize);
            PageInfo<Task> pageInfo = new PageInfo<>(tasks);
            Integer count = (int) pageInfo.getTotal();

            if (count == 0)
                return JSONUtil.returnEntityResult(count, "未查找到加入小组信息", pageInfo);
            else
                return JSONUtil.returnEntityResult(count, "成功获取小组全部任务信息", pageInfo);

        } catch (Exception e) {

            logger.info("数据库发生异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");

        }

    }



    @RequestMapping(value = "/student/task/findOneTask", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findTaskByTaskId(@RequestParam("taskId") Integer taskId) {
        logger.info(taskId.toString());

        if (taskId == null)
            return JSONUtil.returnFailResult("groupId is null");

        try {
            Task task = taskServiceImpl.findTaskByTaskId(taskId);
            if (task == null)
                return JSONUtil.returnFailResult("查找失败，任务或被删除");
            else
                return JSONUtil.returnEntityResult(task);

        } catch (Exception e) {

            logger.info("数据库发生异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");

        }

    }




    @RequestMapping(value = "/student/task/getComment", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAllComment(@RequestParam("taskId") Integer taskId) {
        List<GitHubUserComment> list = new ArrayList<>();
        logger.info(taskId.toString());

        if (taskId == null)
            return JSONUtil.returnFailResult("taskId is null");

        try {
            Task task = taskServiceImpl.findTaskByTaskId(taskId);
            Integer issueNumber = task.getIssueNumber();
            Integer groupId = task.getGroupId();
            Group group = groupServiceImpl.findGroupByGid(groupId);
            Integer ownerId = group.getOwnerId();
            Student owner = studentServiceImpl.findStudentBySid(ownerId);
            if (task == null || group == null || owner == null)
                return JSONUtil.returnFailResult("null pointer exception");
            else {
                String url = String.format(ApiUtil.ISSUE_ALL_COMMENT_URL, new Object[]{owner.getGithubName(), group.getRepositoryUrl(), issueNumber});
                logger.info(url);
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Basic " + SecurityUtils.getSubject().getSession().getAttribute("token"));
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Connection", "keep-Alive");
                header.put("User-Agent", ((Student) SecurityUtils.getSubject().getPrincipal()).getGithubName());

                String result = ApiUtil.ApiGetRequest(url, null, header);
                logger.info(result);
                JSONArray resultJson = JSONArray.parseArray(result);
                int i = 0;
                for (Object o : resultJson) {
                    GitHubUserComment c = new GitHubUserComment();
                    JSONObject jsonObject = (JSONObject) o;
                    String user = jsonObject.getString("user");
                    JSONObject jsonUser = JSONObject.parseObject(user);
                    String login = jsonUser.getString("login");
                    Student s = studentServiceImpl.findStudentByGitHubName(login);
                    if (s == null) {
                        c.setSid(null);
                        c.setRealName(login);
                    }
                    else {
                        c.setSid(s.getSid());
                        if (s.getName() == null)
                            c.setRealName(login);
                        else
                            c.setRealName(s.getName());
                    }
                    String comment = jsonObject.getString("body");
                    String createDate = jsonObject.getString("created_at");
                    String avatar = jsonUser.getString("avatar_url");
                    c.setAvater(avatar);
                    c.setComment(comment);
                    c.setCreateDate(createDate);
                    c.setGithubName(login);
                    c.setId(Integer.parseInt(jsonObject.getString("id")));
                    list.add(c);
                }

                logger.info(list.toString());
                return JSONUtil.returnEntityResult(list);
            }

        } catch (Exception e) {

            logger.info("数据库或网络发生异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库或网络异常");

        }

    }



}
