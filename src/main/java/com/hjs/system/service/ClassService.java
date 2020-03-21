package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Class;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:34
 * @Modified By:
 */
public interface ClassService {

    int deleteClassByCid(Integer cid);

    int insertClass(Class record);

    Class findClassByCid(Integer cid);

    int updateClass(Class record);

    Page<Class> findClassByPage(int pageNo, int pageSize);

    Page<Class> findClassByTid(Integer tid, int pageNo, int pageSize);

    Page<Class> findClassByCourseId(Integer courseId, int pageNo, int pageSize);

    Page<Class> findClassByIsFinished(Boolean isFinished, int pageNo, int pageSize);
}
