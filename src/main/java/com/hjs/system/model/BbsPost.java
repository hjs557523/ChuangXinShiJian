package com.hjs.system.model;

import java.io.Serializable;
import java.util.Date;

public class BbsPost implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer bpId;

    private String title;

    private String content;

    private Date publishTime;

    private Integer publisherId;

    public Integer getBpId() {
        return bpId;
    }

    public void setBpId(Integer bpId) {
        this.bpId = bpId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public String toString() {
        return "BbsPost{" +
                "bpId=" + bpId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishTime=" + publishTime +
                ", publisherId=" + publisherId +
                '}';
    }
}