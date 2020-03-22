package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.BbsReply;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:35
 * @Modified By:
 */
public interface BbsReplyService {

    Page<BbsReply> findBbsReplyByPage(int pageNo, int pageSize);

    Page<BbsReply> findBbsReplyByBpId(Integer bpId, int pageNo, int pageSize);

    Page<BbsReply> findBbsReplyByReviewerId(Integer reviewerId, int pageNo, int pageSize);

    int deleteBbsReplyByBrId(Integer brId);

    int insertBbsReply(BbsReply record);

    BbsReply findBbsReplyByBrId(Integer brId);

    int updateBbsReply(BbsReply record);

}
