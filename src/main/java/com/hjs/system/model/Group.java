package com.hjs.system.model;

import java.io.Serializable;

public class Group implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer gid;

    private String groupName;

    private String repositoryUrl;

    private String oauthToken;

    private Integer stateId;

    private Integer ownerId;

    private Integer subjectId;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Group{" +
                "gid=" + gid +
                ", groupName='" + groupName + '\'' +
                ", repositoryUrl='" + repositoryUrl + '\'' +
                ", oauthToken='" + oauthToken + '\'' +
                ", stateId=" + stateId +
                ", ownerId=" + ownerId +
                ", subjectId=" + subjectId +
                '}';
    }
}