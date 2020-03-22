package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.BbsReplyMapper;
import com.hjs.system.model.BbsReply;
import com.hjs.system.service.BbsReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/22 17:31
 * @Modified By:
 */

@CacheConfig(cacheNames = "bbsReply")
@Service
public class BbsReplyServiceImpl implements BbsReplyService {

    @Autowired
    private BbsReplyMapper bbsReplyMapper;

    @Cacheable
    @Override
    public Page<BbsReply> findBbsReplyByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return bbsReplyMapper.findAllBbsReply();
    }


    @Cacheable
    @Override
    public Page<BbsReply> findBbsReplyByBpId(Integer bpId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return bbsReplyMapper.findBbsReplyByBpId(bpId);
    }


    @Cacheable
    @Override
    public Page<BbsReply> findBbsReplyByReviewerId(Integer reviewerId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return bbsReplyMapper.findBbsReplyByReviewerId(reviewerId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteBbsReplyByBrId(Integer brId) {
        return bbsReplyMapper.deleteBbsReplyByBrId(brId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertBbsReply(BbsReply record) {
        return bbsReplyMapper.insertBbsReply(record);
    }


    @Cacheable
    @Override
    public BbsReply findBbsReplyByBrId(Integer brId) {
        return bbsReplyMapper.findBbsReplyByBrId(brId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateBbsReply(BbsReply record) {
        return bbsReplyMapper.updateBbsReply(record);
    }
}
