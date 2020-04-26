package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.ProcessMapper;
import com.hjs.system.model.Process;
import com.hjs.system.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/22 15:13
 * @Modified By:
 */

@CacheConfig(cacheNames = "process")
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;

    @Cacheable
    @Override
    public Page<Process> findProcessByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return processMapper.findAllProcess();
    }


    @Cacheable
    @Override
    public Page<Process> findProcessByProcessType(Integer processType, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return processMapper.findProcessByProcessType(processType);
    }


    @Cacheable
    @Override
    public Page<Process> findProcessByProcessStatus(Integer processStatus, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return processMapper.findProcessByProcessStatus(processStatus);
    }


    @Cacheable
    @Override
    public Page<Process> findProcessByPublisherId(Integer publisherId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return processMapper.findProcessByPublisherId(publisherId);
    }


    @Cacheable
    @Override
    public Page<Process> findProcessByExecuterId(Integer executerId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return processMapper.findProcessByExecuterId(executerId.toString());
    }


    @Cacheable
    @Override
    public Page<Process> findProcessByGroupId(Integer groupId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return processMapper.findProcessByGroupId(groupId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteProcessByProcessId(Integer processId) {
        return processMapper.deleteProcessByProcessId(processId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertProcess(Process record) {
        return processMapper.insertProcess(record);
    }


    @Cacheable
    @Override
    public Process findProcessByProcessId(Integer processId) {
        return processMapper.findProcessByProcessId(processId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateProcess(Process record) {
        return processMapper.updateProcess(record);
    }

}
