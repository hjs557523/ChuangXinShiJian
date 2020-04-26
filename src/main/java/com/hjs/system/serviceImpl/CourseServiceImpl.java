package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.CourseMapper;
import com.hjs.system.model.Course;
import com.hjs.system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/21 14:14
 * @Modified By:
 */

@CacheConfig(cacheNames = "course")
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Cacheable
    @Override
    public Page<Course> findCourseByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return courseMapper.findAllCourse();
    }


    @Cacheable
    @Override
    public Course findCourseByCid(Integer cid) {
        return courseMapper.findCourseByCid(cid);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int addCourse(Course course) {
        return courseMapper.insertCourse(course);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteCourseByCid(Integer cid) {
        return courseMapper.deleteCourseByCid(cid);
    }
}
