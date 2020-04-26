package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.TaskMapper;
import com.hjs.system.model.Task;
import com.hjs.system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/15 17:33
 * @Modified By:
 */

@CacheConfig(cacheNames = "task")
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteByTaskId(Integer tid) {
        return taskMapper.deleteByPrimaryKey(tid);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertTask(Task record) {
        return taskMapper.insertSelective(record);
    }

    @Cacheable
    @Override
    public Task findTaskByTaskId(Integer tid) {
        return taskMapper.selectByPrimaryKey(tid);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateTask(Task record) {
        return taskMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable
    @Override
    public Page<Task> findAllTaskByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return taskMapper.findAllTask();
    }

    @Cacheable
    @Override
    public Page<Task> findAllTaskByGroupIdAndPage(Integer gid, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return taskMapper.findAllTaskByGroupId(gid);
    }

    @Cacheable
    @Override
    public Page<Task> findAllTaskByStudentIdAndPage(Integer sid, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return taskMapper.findAllTaskByStudentId(sid);
    }
}
