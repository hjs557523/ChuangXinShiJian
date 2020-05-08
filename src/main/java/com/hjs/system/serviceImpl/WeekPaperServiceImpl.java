package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.WeekPaperMapper;
import com.hjs.system.model.WeekPaper;
import com.hjs.system.service.WeekPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/30 18:29
 * @Modified By:
 */

@CacheConfig(cacheNames = "weekPaper")
@Service
public class WeekPaperServiceImpl implements WeekPaperService {

    @Autowired
    private WeekPaperMapper weekPaperMapper;

    @Cacheable
    @Override
    public WeekPaper findWeekPaperByUUID(String uuid) {
        return weekPaperMapper.selectByPrimaryKey(uuid);
    }

    @Cacheable
    @Override
    public Page<WeekPaper> findWeekPaperByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return weekPaperMapper.findAllWeekPaper();
    }


    @Cacheable
    @Override
    public Page<WeekPaper> findWeekPaperBySidAndPage(Integer sid, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return weekPaperMapper.findWeekPaperBySid(sid);
    }

    @Cacheable
    @Override
    public Page<WeekPaper> findWeekPaperByTidAndPage(Integer tid, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return weekPaperMapper.findWeekPaperByTid(tid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int addWeekPaper(WeekPaper weekPaper) {
        return weekPaperMapper.insertSelective(weekPaper);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteTeacherByTid(String uuid) {
        return weekPaperMapper.deleteByPrimaryKey(uuid);
    }
}
