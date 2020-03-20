package com.hjs.system.model;

import java.io.Serializable;

public class ProjectType implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pid;

    private String pType;

    private String remark;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ProjectType{" +
                "pid=" + pid +
                ", pType='" + pType + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}