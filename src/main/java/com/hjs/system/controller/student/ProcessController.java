package com.hjs.system.controller.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hjs.system.runnable.SendMessage;
import com.hjs.system.schedule.WeChatAccessTokenTask;
import com.hjs.system.api.ApiUtil;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.model.*;
import com.hjs.system.model.Process;
import com.hjs.system.service.*;
import com.hjs.system.vo.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/17 13:35
 * @Modified By:
 */

@Controller
public class ProcessController {

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


    @RequestMapping(value = "/student/process/findAllProcess", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllProcessByTaskId(@RequestParam("taskId") Integer taskId, @RequestParam("page")Integer pageNum, @RequestParam("limit")Integer pageSize) {
        logger.info(taskId.toString());
        if (taskId == null)
            return JSONUtil.returnFailResult("taskId null");
        try {
            Page<Process> processes = processServiceImpl.findProcessByProcessType(taskId, pageNum, pageSize);
            PageInfo<Process> pageInfo = new PageInfo<>(processes);
            Integer count = (int) pageInfo.getTotal();
            for (Process p : pageInfo.getList()) {
                // 子任务过期时间判断
                if (p.getProcessStatus() != 1 && p.getEndTime().before(new Date())) {
                    p.setProcessStatus(2);//已过期
                    if (processServiceImpl.updateProcess(p) < 0)
                        return JSONUtil.returnFailResult("数据库异常");
                }
            }
            if (count == 0)
                return JSONUtil.returnEntityResult(count, "未查找到所有子任务信息", pageInfo);
            else
                return JSONUtil.returnEntityResult(count, "成功获取全部子任务信息", pageInfo);
        } catch (Exception e) {
            logger.info("数据库发生异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");
        }
    }



    @RequestMapping(value = "/student/process/addProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addProcess(Process process) {
        logger.info(process.toString());
        if (process == null) {
            return JSONUtil.returnFailResult("process null");
        }

        // 获取当前学生用户
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Group group = groupServiceImpl.findGroupByGid(process.getGroupId());
        Integer ownerId = group.getOwnerId();
        Student owner = studentServiceImpl.findStudentBySid(ownerId);
        String executerIdsStr = process.getExecuterIdList();
        String[] executerIdArr = executerIdsStr.split(",");
        List<String> list = new ArrayList<>();
        List<String> openIdList = new ArrayList<>();
        for (String sid : executerIdArr) {
            Integer intSid = Integer.parseInt(sid);
            Student member = studentServiceImpl.findStudentBySid(intSid);
            list.add(member.getGithubName());
            openIdList.add(member.getOpenId());
        }

        List<String> list2 = new ArrayList<>();
        list2.add(process.getModuleUrl());

        process.setProcessStatus(0);
        process.setCreateTime(new Date());
        process.setPublisherId(student.getSid());
        try {
            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Basic " + SecurityUtils.getSubject().getSession().getAttribute("token"));
            header.put("Content-Type", "application/json; charset=utf-8");
            header.put("Connection", "keep-Alive");
            header.put("User-Agent", ((Student) SecurityUtils.getSubject().getPrincipal()).getGithubName());
            String url = String.format(ApiUtil.REPO_ALL_ISSUES_URL, new Object[]{owner.getGithubName(), group.getRepositoryUrl()});
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", process.getProcessTitle());
            jsonObject.put("body", process.getProcessDetail());
            jsonObject.put("labels", list2);
            jsonObject.put("assignees", list);
            String json = jsonObject.toJSONString();
            String result = ApiUtil.ApiPostRequest(url, json, header);
            JSONObject resultJson = JSONObject.parseObject(result);
            if (resultJson.getString("number") == null) {
                return JSONUtil.returnFailResult("github创建issue失败");
            }

            process.setIssueNumber(Integer.parseInt(resultJson.getString("number")));
            if (processServiceImpl.insertProcess(process) > 0) {
                // 发布消息订阅通知

                for (String openId : openIdList) {
                    WxMssVo wxMssVo = new WxMssVo();
                    wxMssVo.setTouser(openId);
                    wxMssVo.setPage("pages/myTask/myTask");
                    wxMssVo.setTemplate_id("7tHGRJ6DoXw28_YtKewPg4OnKBn4nZRm9tMpI5vU-rs");
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                    Date date = process.getEndTime();
                    String endTime = formatter.format(date);
                    Map<String, TemplateData> map = new HashMap<>(4);
                    map.put("thing1", new TemplateData(process.getProcessTitle()));
                    map.put("date2", new TemplateData(endTime));
                    map.put("phrase3", new TemplateData(process.getModuleUrl()));
                    map.put("thing4", new TemplateData(process.getProcessDetail()));
                    wxMssVo.setData(map);

                    String wxAccessToken = weChatAccessTokenTask.getWxAccessToken();
                    SendMessage sendMessage = new SendMessage(wxMssVo, wxAccessToken);
                    executorService.execute(sendMessage);// 发送消息订阅
                }

                return JSONUtil.returnSuccessResult("添加子任务成功");
            } else {
                return JSONUtil.returnFailResult("添加子任务失败");
            }

        } catch (Exception e) {
            logger.info("数据库异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");
        }

    }



    @RequestMapping(value = "/student/process/editProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String editProcess(Process process) {
        logger.info(process.toString());
        if (process == null) {
            return JSONUtil.returnFailResult("process null");
        }

        if (process.getEndTime() == null)
            return JSONUtil.returnFailResult("process endTime null");
        try {
            if (process.getEndTime().after(new Date())) {
                logger.info("截止时间延长");
                process.setProcessStatus(0);
            }
            if (processServiceImpl.updateProcess(process) > 0) {
                return JSONUtil.returnSuccessResult("修改子任务成功");
            } else {
                return JSONUtil.returnFailResult("修改子任务失败");
            }
        } catch (Exception e) {
            logger.info("数据库异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");
        }

    }


    @RequestMapping(value = "/student/process/finishProcess", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String finishProcess(@RequestParam("processId") Integer processId) {
        if (processId == null) {
            return JSONUtil.returnFailResult("processId null");
        }

        try {
            Process process = processServiceImpl.findProcessByProcessId(processId);
            if (process == null) {
                return JSONUtil.returnFailResult("数据库查找失败");
            }

            process.setProcessStatus(1);
            if (processServiceImpl.updateProcess(process) > 0) {
                return JSONUtil.returnSuccessResult("修改状态成功");
            } else {
                return JSONUtil.returnFailResult("修改状态失败");
            }
        } catch (Exception e) {
            logger.info("数据库异常: " + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");
        }

    }

    @RequestMapping(value = "/student/process/getLabels", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getRepoLabels(@RequestParam("groupId") Integer groupId) {
        if (groupId == null) {
            return JSONUtil.returnFailResult("groupId null");
        }

        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        String githubName = student.getGithubName();
        String accessToken = (String) SecurityUtils.getSubject().getSession().getAttribute("token");

        try {
            Group group = groupServiceImpl.findGroupByGid(groupId);
            if (group == null)
                return JSONUtil.returnFailResult("fail1");
            String repo = group.getRepositoryUrl();
            List<String> list = ApiUtil.getRepoAllLabels(githubName, repo, accessToken);
            return JSONUtil.returnEntityResult(list);

        } catch (Exception e) {
            return JSONUtil.returnFailResult("fail2");
        }

    }



    @RequestMapping(value = "/student/process/taskStatistic", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTaskStatistic(@RequestParam("groupId") Integer groupId) {
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


    @RequestMapping(value = "/student/process/commitStatistic", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
            String myGithub = ((Student) SecurityUtils.getSubject().getPrincipal()).getGithubName();
            List<CommitStatistic> list = ApiUtil.getMemberCommit(ownerGithub, repo, accessToken, myGithub);
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



    @RequestMapping(value = "/student/process/getAllMyProcesses", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getMyAllDoProcess() {
        Map<String, List<ProcessInfo>> map = new HashMap<>();
        List<ProcessInfo> myDoProcesses = new ArrayList<>();
        List<ProcessInfo> myFinishedProcesses = new ArrayList<>();
        try {
            Student student = (Student) SecurityUtils.getSubject().getPrincipal();
            Integer sid = student.getSid();
            Page<Process> processes = processServiceImpl.findProcessByExecuterId(sid, 0, 0);
            PageInfo<Process> pageInfo = new PageInfo<>(processes);
            for (Process process : pageInfo.getList()) {
                if (process.getProcessStatus() != 1) {
                    String[] arr = process.getExecuterIdList().split(",");
                    for (String a : arr) {
                        if (a.equals(sid.toString())) {
                            ProcessInfo processInfo = new ProcessInfo();
                            processInfo.setProcess(process);
                            List<Student> list = new ArrayList<>();
                            for (String b : arr) {
                                Student s = studentServiceImpl.findStudentBySid(Integer.parseInt(b));
                                list.add(s);
                            }
                            Integer publisherId = process.getPublisherId();
                            Integer groupId = process.getGroupId();
                            Integer taskId = process.getProcessType();
                            Student publisher = studentServiceImpl.findStudentBySid(publisherId);
                            Group group = groupServiceImpl.findGroupByGid(groupId);
                            Task task = taskServiceImpl.findTaskByTaskId(taskId);

                            processInfo.setPublisher(publisher);
                            processInfo.setExecutorList(list);
                            processInfo.setGroup(group);
                            processInfo.setTask(task);
                            myDoProcesses.add(processInfo);
                            break;
                        }
                    }
                } else {
                    String[] arr = process.getExecuterIdList().split(",");
                    for (String a : arr) {
                        if (a.equals(sid.toString())) {
                            ProcessInfo processInfo = new ProcessInfo();
                            processInfo.setProcess(process);
                            List<Student> list = new ArrayList<>();
                            for (String b : arr) {
                                Student s = studentServiceImpl.findStudentBySid(Integer.parseInt(b));
                                list.add(s);
                            }
                            Integer publisherId = process.getPublisherId();
                            Integer groupId = process.getGroupId();
                            Integer taskId = process.getProcessType();
                            logger.info(groupId.toString());

                            Student publisher = studentServiceImpl.findStudentBySid(publisherId);
                            Group group = groupServiceImpl.findGroupByGid(groupId);
                            Task task = taskServiceImpl.findTaskByTaskId(taskId);

                            processInfo.setPublisher(publisher);
                            processInfo.setExecutorList(list);
                            processInfo.setGroup(group);
                            processInfo.setTask(task);
                            myFinishedProcesses.add(processInfo);
                            break;
                        }
                    }

                }
            }

            map.put("undo", myDoProcesses);
            map.put("finished", myFinishedProcesses);

            return JSONUtil.returnEntityResult(map);

        } catch (Exception e) {
            logger.info("异常: " + e.getMessage());
            return JSONUtil.returnFailResult("error2");
        }

    }




}
