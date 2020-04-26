package com.hjs.system.vo;

import java.io.Serializable;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/24 18:56
 * @Modified By:
 */
public class TaskStatistic implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer sid;

    private String studentId;

    private String name;

    private String githubName;

    private Integer count;

    private Integer finish;

    private float percent;

    private String avatar;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGithubName() {
        return githubName;
    }

    public void setGithubName(String githubName) {
        this.githubName = githubName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "TaskStatistic{" +
                "sid=" + sid +
                ", studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", githubName='" + githubName + '\'' +
                ", count=" + count +
                ", finish=" + finish +
                ", percent=" + percent +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
