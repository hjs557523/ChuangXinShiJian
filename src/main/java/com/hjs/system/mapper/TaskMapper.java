package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMapper {

    int deleteByPrimaryKey(Integer tid);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(Task record);

    Page<Task> findAllTask();

    Page<Task> findAllTaskByGroupId(Integer gid);

    Page<Task> findAllTaskByStudentId(Integer sid);
}