package com.hjs.system.model;

import java.util.Date;

public class Process {
    private Integer processId;

    private Boolean processType;

    private Boolean processStatus;

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

    public Boolean getProcessType() {
        return processType;
    }

    public void setProcessType(Boolean processType) {
        this.processType = processType;
    }

    public Boolean getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Boolean processStatus) {
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
}