package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.BbsPostMapper;
import com.hjs.system.model.BbsPost;
import com.hjs.system.service.BbsPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/22 16:10
 * @Modified By:
 */

@CacheConfig(cacheNames = "bbsPost")
@Service
public class BbsPostServiceImpl implements BbsPostService {

    @Autowired
    private BbsPostMapper bbsPostMapper;


    @Cacheable
    @Override
    public Page<BbsPost> findBbsPostByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return bbsPostMapper.findAllBbsPost();
    }


    @Cacheable
    @Override
    public Page<BbsPost> findBbsPostByPublisherId(Integer publisherId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return bbsPostMapper.findBbsPostByPublisherId(publisherId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteBbsPostByBpId(Integer bpId) {
        return bbsPostMapper.deleteBbsPostByBpId(bpId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertBbsPost(BbsPost record) {
        return bbsPostMapper.insertBbsPost(record);
    }


    @Cacheable
    @Override
    public BbsPost findBbsPostByBpId(Integer bpId) {
        return bbsPostMapper.findBbsPostByBpId(bpId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateBbsPost(BbsPost record) {
        return bbsPostMapper.updateBbsPost(record);
    }

}
