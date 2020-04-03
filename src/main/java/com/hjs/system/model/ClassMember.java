package com.hjs.system.model;

import java.io.Serializable;

public class ClassMember implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer classMemberId;

    private Integer classId;

    private Integer studentId;

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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}