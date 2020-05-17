package com.hjs.system.controller.teacher;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.api.ApiUtil;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.controller.student.ProcessController;
import com.hjs.system.model.*;
import com.hjs.system.model.Process;
import com.hjs.system.schedule.WeChatAccessTokenTask;
import com.hjs.system.service.*;
import com.hjs.system.vo.CommitStatistic;
import com.hjs.system.vo.ProcessInfo;
import com.hjs.system.vo.TaskStatistic;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/5/6 18:52
 * @Modified By:
 */
@Controller
public class TeacherProcessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private WeChatAccessTokenTask weChatAccessTokenTask;

    @Autowired
    private ProcessService processServiceImpl;

    @Autowired
    private GroupService groupServiceImpl;

    @Autowired
    private GroupMemberService groupMemberServiceImpl;

    @Autowired
    private StudentService studentServiceImpl;

    @Autowired
    private TaskService taskServiceImpl;


    @RequestMapping(value = "/teacher/process/taskStatistic", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTaskStatistic(@RequestParam("groupId") Integer groupId) {
        DecimalFormat df = new DecimalFormat("0.00");
        List<TaskStatistic> result = new ArrayList<>();

        Map<String, TaskStatistic> map = new HashMap<>();
        logger.info(groupId.toString());
        if (groupId == null)
            return JSONUtil.returnFailResult("groupId null");
        try {
            // 查找小组的所有成员
            Page<GroupMember> memberPage = groupMemberServiceImpl.findGroupMemberByGroupId(groupId, 0, 0);
            PageInfo<GroupMember> memberPageInfo = new PageInfo<>(memberPage);
            for (GroupMember groupMember : memberPageInfo.getList()) {
                TaskStatistic taskStatistic = new TaskStatistic();
                taskStatistic.setName(groupMember.getStudent().getName());
                taskStatistic.setSid(groupMember.getStudent().getSid());
                taskStatistic.setGithubName(groupMember.getStudent().getGithubName());
                taskStatistic.setStudentId(groupMember.getStudent().getStudentId());
                taskStatistic.setAvatar(groupMember.getStudent().getPicImg());
                taskStatistic.setCount(0); // 初始化任务数为0
                taskStatistic.setFinish(0);
                taskStatistic.setPercent(0);
                map.put(groupMember.getStudent().getSid().toString(), taskStatistic);
            }

            // 不分页
            Page<Process> processes = processServiceImpl.findProcessByGroupId(groupId, 0, 0);
            PageInfo<Process> pageInfo = new PageInfo<>(processes);
            Integer count2 = (int) pageInfo.getTotal();
            for (Process p : pageInfo.getList()) {
                String executorIdList = p.getExecuterIdList();
                String[] arr = executorIdList.split(",");
                Integer status = p.getProcessStatus();
                for (String s : arr) {
                    Integer count = map.get(s).getCount();
                    map.get(s).setCount(++count);
                    if (status == 1) {
                        Integer Finish = map.get(s).getFinish();
                        map.get(s).setFinish(++Finish);
                    }
                }
            }

            for (String key : map.keySet()) {
                map.get(key).setPercent(((float) map.get(key).getFinish() / map.get(key).getCount()) * 100);
                result.add(map.get(key));
            }

            // jdk8: list.sort(new Comparator() { public int compare(Object o1, Object o2) {}})
            // jdk8以下: Collections.sort(list, new Comparator<TaskStatistic>() {public int compare(TaskStatistic t1, TaskStatistic t2){}})
            result.sort(new Comparator<TaskStatistic>() {
                @Override
                public int compare(TaskStatistic o1, TaskStatistic o2) {
                    if (o1.getFinish() < o2.getFinish())
                        return 1;
                    else if (o1.getFinish() > o2.getFinish())
                        return -1;
                    else
                        return 0;
                }
            });

            return JSONUtil.returnEntityResult(result);

        } catch (Exception e) {
            logger.info("数据库发生异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");
        }
    }


    @RequestMapping(value = "/teacher/process/commitStatistic", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCommitStatistic(@RequestParam("groupId") Integer groupId) {
        if (groupId == null)
            return JSONUtil.returnFailResult("groupId null");
        try {
            Group group = groupServiceImpl.findGroupByGid(groupId);
            String repo = group.getRepositoryUrl();
            Integer ownerId = group.getOwnerId();
            Student owner = studentServiceImpl.findStudentBySid(ownerId);
            String ownerGithub = owner.getGithubName();
            String accessToken = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
            String myGithub = ((Teacher) SecurityUtils.getSubject().getPrincipal()).getGithubName();
            List<CommitStatistic> list = ApiUtil.getMemberCommit2(ownerGithub, repo, accessToken, myGithub);
            if (list == null)
                return JSONUtil.returnFailResult("error1");
            for (CommitStatistic c : list) {
                String githubName = c.getGithubName();
                String realName = studentServiceImpl.findStudentByGitHubName(githubName).getName();
                c.setRealName(realName);
            }

            return JSONUtil.returnEntityResult(list);

        } catch (Exception e) {
            logger.info("异常: " + e.getMessage());
            return JSONUtil.returnFailResult("error2");
        }

    }


    @RequestMapping(value = "/teacher/process/getAllProcessInfoByGid", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getGroupAllProcessInfo(@RequestParam("groupId") Integer groupId) {
        List<ProcessInfo> processInfoList = new ArrayList<>();
        if (groupId == null)
            return JSONUtil.returnFailResult("groupId null");
        try {
            Page<Process> processes = processServiceImpl.findProcessByGroupId(groupId, 0, 0);
            PageInfo<Process> processPageInfo = new PageInfo<>(processes);
            for (Process process : processPageInfo.getList()) {
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setProcess(process);
                String[] arr = process.getExecuterIdList().split(",");
                List<Student> students = new ArrayList<>();
                for (String s : arr) {
                    Student student = studentServiceImpl.findStudentBySid(Integer.parseInt(s));
                    students.add(student);
                }
                processInfo.setExecutorList(students);
                Student student = studentServiceImpl.findStudentBySid(process.getPublisherId());
                processInfo.setPublisher(student);
                Task task = taskServiceImpl.findTaskByTaskId(process.getProcessType());
                processInfo.setTask(task);
                processInfoList.add(processInfo);
            }

            return JSONUtil.returnEntityResult(processInfoList);

        } catch (Exception e) {
            logger.info("数据库异常: " + e.getMessage());
        }
        return null;
    }
}
