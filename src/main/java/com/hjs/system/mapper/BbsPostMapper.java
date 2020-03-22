package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.BbsPost;
import org.springframework.stereotype.Repository;


@Repository
public interface BbsPostMapper {

    Page<BbsPost> findAllBbsPost();

    Page<BbsPost> findBbsPostByPublisherId(Integer publisherId);

    int deleteBbsPostByBpId(Integer bpId);

    int insertBbsPost(BbsPost record);

    BbsPost findBbsPostByBpId(Integer bpId);

    int updateBbsPost(BbsPost record);

}