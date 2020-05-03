package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Teacher;
import com.hjs.system.model.WeekPaper;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/30 18:22
 * @Modified By:
 */
public interface WeekPaperService {

    Page<WeekPaper> findWeekPaperByPage(int pageNo, int pageSize);

    Page<WeekPaper> findWeekPaperBySidAndPage(Integer sid, int pageNo, int pageSize);

    Page<WeekPaper> findWeekPaperByTidAndPage(Integer tid, int pageNo, int pageSize);

    int addWeekPaper(WeekPaper weekPaper);

    int deleteTeacherByTid(String uuid);

}
