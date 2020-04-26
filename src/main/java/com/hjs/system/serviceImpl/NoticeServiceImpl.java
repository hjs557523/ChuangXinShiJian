package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.NoticeMapper;
import com.hjs.system.model.Notice;
import com.hjs.system.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/22 13:44
 * @Modified By:
 */

@CacheConfig(cacheNames = "notice")
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;


    @Cacheable
    @Override
    public Page<Notice> findNoticeByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return noticeMapper.findAllNotice();
    }


    @Cacheable
    @Override
    public Page<Notice> findNoticeByTypeId(Integer typeId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return noticeMapper.findNoticeByTypeId(typeId);
    }


    @Cacheable
    @Override
    public Page<Notice> findNoticeByAuthorId(Integer authorId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return noticeMapper.findNoticeByAuthorId(authorId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteNoticeByNid(Integer nid) {
        return noticeMapper.deleteNoticeByNid(nid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertNotice(Notice record) {
        return noticeMapper.insertNotice(record);
    }


    @Cacheable
    @Override
    public Notice findNoticeByNid(Integer nid) {
        return noticeMapper.findNoticeByNid(nid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateNotice(Notice record) {
        return noticeMapper.updateNotice(record);
    }

}
