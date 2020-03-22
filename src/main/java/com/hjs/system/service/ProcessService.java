package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Process;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:28
 * @Modified By:
 */
public interface ProcessService {

    Page<Process> findProcessByPage(int pageNo, int pageSize);

    Page<Process> findProcessByProcessType(Integer processType, int pageNo, int pageSize);

    Page<Process> findProcessByProcessStatus(Integer processStatus, int pageNo, int pageSize);

    Page<Process> findProcessByPublisherId(Integer publisherId, int pageNo, int pageSize);

    Page<Process> findProcessByExecuterId(Integer executerId, int pageNo, int pageSize);

    Page<Process> findProcessByGroupId(Integer groupId, int pageNo, int pageSize);

    int deleteProcessByProcessId(Integer processId);

    int insertProcess(Process record);

    Process findProcessByProcessId(Integer processId);

    int updateProcess(Process record);

}
