package com.hjs.system.model;

import java.io.Serializable;

public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer subjectId;

    private Integer tid;

    private String subjectName;

    private String subjectDetail;

    private String remark;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDetail() {
        return subjectDetail;
    }

    public void setSubjectDetail(String subjectDetail) {
        this.subjectDetail = subjectDetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", tid=" + tid +
                ", subjectName='" + subjectName + '\'' +
                ", subjectDetail='" + subjectDetail + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}