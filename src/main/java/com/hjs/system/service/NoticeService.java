package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Notice;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:30
 * @Modified By:
 */
public interface NoticeService {

    Page<Notice> findNoticeByPage(int pageNo, int pageSize);

    Page<Notice> findNoticeByTypeId(Integer typeId, int pageNo, int pageSize);

    Page<Notice> findNoticeByAuthorId(Integer authorId, int pageNo, int pageSize);

    int deleteNoticeByNid(Integer nid);

    int insertNotice(Notice record);

    Notice findNoticeByNid(Integer nid);

    int updateNotice(Notice record);

}
