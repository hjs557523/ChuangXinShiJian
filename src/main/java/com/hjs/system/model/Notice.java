package com.hjs.system.model;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer nid;

    private String topic;

    private String detail;

    private Date createTime;

    private Integer typeId;

    private Integer authorId;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "nid=" + nid +
                ", topic='" + topic + '\'' +
                ", detail='" + detail + '\'' +
                ", createTime=" + createTime +
                ", typeId=" + typeId +
                ", authorId=" + authorId +
                '}';
    }
}