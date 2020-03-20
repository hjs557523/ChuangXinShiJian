package com.hjs.system.model;

import java.io.Serializable;
import java.util.Date;

public class BbsReply implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer brId;

    private String content;

    private Integer bpId;

    private Date replyTime;

    private Integer reviewerId;

    public Integer getBrId() {
        return brId;
    }

    public void setBrId(Integer brId) {
        this.brId = brId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBpId() {
        return bpId;
    }

    public void setBpId(Integer bpId) {
        this.bpId = bpId;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    @Override
    public String toString() {
        return "BbsReply{" +
                "brId=" + brId +
                ", content='" + content + '\'' +
                ", bpId=" + bpId +
                ", replyTime=" + replyTime +
                ", reviewerId=" + reviewerId +
                '}';
    }
}