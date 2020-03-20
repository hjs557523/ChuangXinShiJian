package com.hjs.system.mapper;

import com.hjs.system.model.BbsPost;
import org.springframework.stereotype.Repository;


@Repository
public interface BbsPostMapper {

    int deleteBbsPostByBpId(Integer bpId);

    int insertBbsPost(BbsPost record);

    BbsPost findBbsPostByBpId(Integer bpId);

    int updateBbsPost(BbsPost record);

}