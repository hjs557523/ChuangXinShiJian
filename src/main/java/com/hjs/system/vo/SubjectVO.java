package com.hjs.system.vo;

import com.hjs.system.model.Subject;
import com.hjs.system.model.Teacher;
import com.sun.xml.internal.rngom.binary.DataExceptPattern;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/5/7 14:35
 * @Modified By:
 */
public class SubjectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Subject subject;

    private Integer selected;


    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "SubjectVO{" +
                "subject=" + subject +
                ", selected=" + selected +
                '}';
    }
}
