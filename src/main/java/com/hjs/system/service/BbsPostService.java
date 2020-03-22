package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.BbsPost;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:35
 * @Modified By:
 */
public interface BbsPostService {

    Page<BbsPost> findBbsPostByPage(int pageNo, int pageSize);

    Page<BbsPost> findBbsPostByPublisherId(Integer publisherId, int pageNo, int pageSize);

    int deleteBbsPostByBpId(Integer bpId);

    int insertBbsPost(BbsPost record);

    BbsPost findBbsPostByBpId(Integer bpId);

    int updateBbsPost(BbsPost record);

}
