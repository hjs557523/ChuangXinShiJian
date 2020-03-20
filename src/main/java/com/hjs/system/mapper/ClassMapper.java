package com.hjs.system.mapper;

import com.hjs.system.model.Class;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassMapper {
    int deleteClassByCid(Integer cid);

    int insertClass(Class record);

    Class findClassByCid(Integer cid);

    int updateClass(Class record);

}