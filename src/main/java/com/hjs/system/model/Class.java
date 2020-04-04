package com.hjs.system.model;

import java.io.Serializable;

public class Class implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer cid;

    private String className;

//    private Integer tid;

//    private Integer courseId;

    private Teacher teacher;

    private Course course;

    private Boolean isFinished;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

//    public Integer getTid() {
//        return tid;
//    }
//
//    public void setTid(Integer tid) {
//        this.tid = tid;
//    }

//    public Integer getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(Integer courseId) {
//        this.courseId = courseId;
//    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "Class{" +
                "cid=" + cid +
                ", className='" + className + '\'' +
                ", teacher=" + teacher +
                ", course=" + course +
                ", isFinished=" + isFinished +
                '}';
    }
}