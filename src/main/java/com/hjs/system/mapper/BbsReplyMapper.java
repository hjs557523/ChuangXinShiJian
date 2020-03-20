package com.hjs.system.mapper;

import com.hjs.system.model.BbsReply;
import org.springframework.stereotype.Repository;


@Repository
public interface BbsReplyMapper {

    int deleteByPrimaryKey(Integer brId);

    int insert(BbsReply record);

    int insertSelective(BbsReply record);

    BbsReply selectByPrimaryKey(Integer brId);

    int updateByPrimaryKeySelective(BbsReply record);

    int updateByPrimaryKey(BbsReply record);
}