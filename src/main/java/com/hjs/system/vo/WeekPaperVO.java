package com.hjs.system.vo;

import com.hjs.system.model.Student;
import com.hjs.system.model.WeekPaper;

import java.io.Serializable;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/5/8 12:43
 * @Modified By:
 */
public class WeekPaperVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private WeekPaper weekPaper;

    private Student student;

    public WeekPaper getWeekPaper() {
        return weekPaper;
    }

    public void setWeekPaper(WeekPaper weekPaper) {
        this.weekPaper = weekPaper;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "WeekPaperVO{" +
                "weekPaper=" + weekPaper +
                ", student=" + student +
                '}';
    }
}
