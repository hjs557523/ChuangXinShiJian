package com.hjs.system.vo;

import com.hjs.system.model.Process;
import com.hjs.system.model.Student;

import java.io.Serializable;
import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/28 22:07
 * @Modified By:
 */
public class ProcessInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Process process;

    private List<Student> executorList;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public List<Student> getExecutorList() {
        return executorList;
    }

    public void setExecutorList(List<Student> executorList) {
        this.executorList = executorList;
    }

    @Override
    public String toString() {
        return "ProcessInfo{" +
                "process=" + process +
                ", executorList=" + executorList +
                '}';
    }
}
