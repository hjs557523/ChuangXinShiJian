package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.Process;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessMapper {

    Page<Process> findAllProcess();

    Page<Process> findProcessByProcessType(Integer processType);

    Page<Process> findProcessByProcessStatus(Integer processStatus);

    Page<Process> findProcessByPublisherId(Integer publisherId);

    Page<Process> findProcessByExecuterId(String executerId);

    Page<Process> findProcessByGroupId(Integer groupId);

    int deleteProcessByProcessId(Integer processId);

    int insertProcess(Process record);

    Process findProcessByProcessId(Integer processId);

    int updateProcess(Process record);
}