package com.hjs.system.mapper;

import com.hjs.system.model.Process;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessMapper {

    int deleteProcessByProcessId(Integer processId);

    int insertProcess(Process record);

    Process findProcessByProcessId(Integer processId);

    int updateProcess(Process record);
}