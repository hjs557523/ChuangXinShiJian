package com.hjs.system.mapper;

import com.hjs.system.model.Course;

public interface CourseMapper {
    int deleteByPrimaryKey(Boolean cid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Boolean cid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}