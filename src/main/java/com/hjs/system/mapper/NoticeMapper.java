package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.Notice;
import org.springframework.stereotype.Repository;


@Repository
public interface NoticeMapper {

    Page<Notice> findAllNotice();

    Page<Notice> findNoticeByTypeId(Integer typeId);

    Page<Notice> findNoticeByAuthorId(Integer authorId);

    int deleteNoticeByNid(Integer nid);

    int insertNotice(Notice record);

    Notice findNoticeByNid(Integer nid);

    int updateNotice(Notice record);

}