package com.hjs.system.vo;

import java.io.Serializable;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/23 14:28
 * @Modified By:
 */
public class GitHubUserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer sid;

    private String realName;

    private String githubName;

    private String comment;

    private String createDate;

    private String avater;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGithubName() {
        return githubName;
    }

    public void setGithubName(String githubName) {
        this.githubName = githubName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    @Override
    public String toString() {
        return "GitHubUserComment{" +
                "id=" + id +
                ", sid=" + sid +
                ", realName='" + realName + '\'' +
                ", githubName='" + githubName + '\'' +
                ", comment='" + comment + '\'' +
                ", createDate='" + createDate + '\'' +
                ", avater='" + avater + '\'' +
                '}';
    }
}
