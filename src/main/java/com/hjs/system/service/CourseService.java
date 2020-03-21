package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Course;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:33
 * @Modified By:
 */

public interface CourseService {

    Page<Course> findCourseByPage(int pageNo, int pageSize);

    Course findCourseByCid(Integer cid);

    int updateCourse(Course course);

    int addCourse(Course course);

    int deleteCourseByCid(Integer cid);

}
