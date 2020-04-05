package com.hjs.system.model;

import java.io.Serializable;

/**
 * 班级成员中间表
 */
public class ClassMember implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer classMemberId;

//    private Class classInfo;

    private Integer classId;

//    private Integer studentId;

    private Student student;

    private Boolean accept;

    public Integer getClassMemberId() {
        return classMemberId;
    }

    public void setClassMemberId(Integer classMemberId) {
        this.classMemberId = classMemberId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    @Override
    public String toString() {
        return "ClassMember{" +
                "classMemberId=" + classMemberId +
                ", classId=" + classId +
                ", student=" + student +
                ", accept=" + accept +
                '}';
    }
}