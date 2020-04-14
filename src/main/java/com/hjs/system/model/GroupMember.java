package com.hjs.system.model;

import java.io.Serializable;

public class GroupMember implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer groupMemberId;

//    private Integer groupId;

//    private Integer studentId;

    private Group group;

    private Student student;

    public Integer getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(Integer groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "groupMemberId=" + groupMemberId +
                ", group=" + group +
                ", student=" + student +
                '}';
    }
}