package com.hjs.system.model;

import java.io.Serializable;

public class GroupMember implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer groupMemberId;

    private Integer groupId;

//    private Integer studentId;

    private Student student;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "groupMemberId=" + groupMemberId +
                ", groupId=" + groupId +
                ", student=" + student +
                '}';
    }
}