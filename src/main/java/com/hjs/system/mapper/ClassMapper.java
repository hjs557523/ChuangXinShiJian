package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.Class;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassMapper {
    int deleteClassByCid(Integer cid);

    int insertClass(Class record);

    Class findClassByCid(Integer cid);

    int updateClass(Class record);

    Page<Class> findAllClass();

    Page<Class> findClassByTid(Integer tid);

    Page<Class> findClassByCourseId(Integer courseId);

    Page<Class> findClassByIsFinished(Boolean isFinished);

}