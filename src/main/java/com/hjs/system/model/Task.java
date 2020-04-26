package com.hjs.system.model;

import java.io.Serializable;

public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer tid;

    private String taskName;

    private Integer groupId;

    private Student student;

    private Integer finished;

    private Integer issueNumber;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public String toString() {
        return "Task{" +
                "tid=" + tid +
                ", taskName='" + taskName + '\'' +
                ", groupId=" + groupId +
                ", student=" + student +
                ", finished=" + finished +
                ", issueNumber=" + issueNumber +
                '}';
    }
}