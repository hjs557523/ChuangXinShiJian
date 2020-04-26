package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Task;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/15 17:05
 * @Modified By:
 */
public interface TaskService {

    int deleteByTaskId(Integer tid);

    int insertTask(Task record);

    Task findTaskByTaskId(Integer tid);

    int updateTask(Task record);

    Page<Task> findAllTaskByPage(int pageNo, int pageSize);

    Page<Task> findAllTaskByGroupIdAndPage(Integer gid, int pageNo, int pageSize);

    Page<Task> findAllTaskByStudentIdAndPage(Integer sid, int pageNo, int pageSize);


}
