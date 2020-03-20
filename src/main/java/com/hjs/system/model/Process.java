package com.hjs.system.model;

import java.io.Serializable;
import java.util.Date;

public class Process implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer processId;

    private Integer processType;

    private Integer processStatus;

    private String processTitle;

    private String processDetail;

    private Integer publisherId;

    private String executerIdList;

    private Date createTime;

    private Date endTime;

    private Integer groupId;

    private String moduleUrl;

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessTitle() {
        return processTitle;
    }

    public void setProcessTitle(String processTitle) {
        this.processTitle = processTitle;
    }

    public String getProcessDetail() {
        return processDetail;
    }

    public void setProcessDetail(String processDetail) {
        this.processDetail = processDetail;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getExecuterIdList() {
        return executerIdList;
    }

    public void setExecuterIdList(String executerIdList) {
        this.executerIdList = executerIdList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    @Override
    public String toString() {
        return "Process{" +
                "processId=" + processId +
                ", processType=" + processType +
                ", processStatus=" + processStatus +
                ", processTitle='" + processTitle + '\'' +
                ", processDetail='" + processDetail + '\'' +
                ", publisherId=" + publisherId +
                ", executerIdList='" + executerIdList + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", groupId=" + groupId +
                ", moduleUrl='" + moduleUrl + '\'' +
                '}';
    }
}