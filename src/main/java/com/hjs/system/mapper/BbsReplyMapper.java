package com.hjs.system.mapper;

import com.hjs.system.model.BbsReply;
import org.springframework.stereotype.Repository;


@Repository
public interface BbsReplyMapper {

    int deleteBbsReplyByBrId(Integer brId);

    int insertBbsReply(BbsReply record);

    BbsReply findBbsReplyByBrId(Integer brId);

    int updateBbsReply(BbsReply record);

}