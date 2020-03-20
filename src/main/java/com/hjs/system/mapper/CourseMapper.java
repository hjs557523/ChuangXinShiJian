package com.hjs.system.mapper;

import com.hjs.system.model.Course;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseMapper {

    int deleteCourseByCid(Integer cid);

    int insertCourse(Course record);

    Course findCourseByCid(Integer cid);

    int updateCourse(Course record);

}