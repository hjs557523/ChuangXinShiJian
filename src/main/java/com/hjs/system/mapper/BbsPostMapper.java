package com.hjs.system.mapper;

import com.hjs.system.model.BbsPost;

public interface BbsPostMapper {
    int deleteByPrimaryKey(Integer bpId);

    int insert(BbsPost record);

    int insertSelective(BbsPost record);

    BbsPost selectByPrimaryKey(Integer bpId);

    int updateByPrimaryKeySelective(BbsPost record);

    int updateByPrimaryKey(BbsPost record);
}