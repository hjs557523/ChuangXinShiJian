package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.BbsReply;
import org.springframework.stereotype.Repository;


@Repository
public interface BbsReplyMapper {

    Page<BbsReply> findAllBbsReply();

    Page<BbsReply> findBbsReplyByBpId(Integer bpId);

    Page<BbsReply> findBbsReplyByReviewerId(Integer reviewerId);

    int deleteBbsReplyByBrId(Integer brId);

    int insertBbsReply(BbsReply record);

    BbsReply findBbsReplyByBrId(Integer brId);

    int updateBbsReply(BbsReply record);

}