package com.hjs.system.model;

import java.io.Serializable;
import java.util.Date;

public class WeekPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String thisWeekWork;

    private String nextWeekWork;

    private Integer sid;

    private Integer tid;

    private Date createTime;

    private String file;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getThisWeekWork() {
        return thisWeekWork;
    }

    public void setThisWeekWork(String thisWeekWork) {
        this.thisWeekWork = thisWeekWork;
    }

    public String getNextWeekWork() {
        return nextWeekWork;
    }

    public void setNextWeekWork(String nextWeekWork) {
        this.nextWeekWork = nextWeekWork;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "WeekPaper{" +
                "uuid='" + uuid + '\'' +
                ", thisWeekWork='" + thisWeekWork + '\'' +
                ", nextWeekWork='" + nextWeekWork + '\'' +
                ", sid=" + sid +
                ", tid=" + tid +
                ", createTime=" + createTime +
                ", file='" + file + '\'' +
                '}';
    }
}