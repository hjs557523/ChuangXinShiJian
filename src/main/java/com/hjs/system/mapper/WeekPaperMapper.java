package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.WeekPaper;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekPaperMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(WeekPaper record);

    int insertSelective(WeekPaper record);

    WeekPaper selectByPrimaryKey(String uuid);

    Page<WeekPaper> findAllWeekPaper();

    Page<WeekPaper> findWeekPaperBySid(Integer sid);

    Page<WeekPaper> findWeekPaperByTid(Integer tid);

    int updateByPrimaryKeySelective(WeekPaper record);

    int updateByPrimaryKey(WeekPaper record);
}