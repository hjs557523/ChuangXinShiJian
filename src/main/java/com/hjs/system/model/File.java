package com.hjs.system.model;

import java.io.Serializable;
import java.util.Date;

public class File implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer fileId;

    private String fileName;

    private String uploadPath;

    private Date uploadTime;

    private Integer fileType;

    private Integer groupId;

    private Integer authorId;

    private String title;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                ", uploadTime=" + uploadTime +
                ", fileType=" + fileType +
                ", groupId=" + groupId +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                '}';
    }
}