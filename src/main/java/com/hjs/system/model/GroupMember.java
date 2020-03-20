package com.hjs.system.model;

import java.io.Serializable;

public class GroupMember implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer groupMemberId;

    private Integer groupId;

    private Integer studentId;

    public Integer getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(Integer groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "groupMemberId=" + groupMemberId +
                ", groupId=" + groupId +
                ", studentId=" + studentId +
                '}';
    }
}