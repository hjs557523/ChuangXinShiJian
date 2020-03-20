package com.hjs.system.mapper;

import com.hjs.system.model.Notice;
import org.springframework.stereotype.Repository;


@Repository
public interface NoticeMapper {

    int deleteNoticeByNid(Integer nid);

    int insertNotice(Notice record);

    Notice findNoticeByNid(Integer nid);

    int updateNotice(Notice record);

}