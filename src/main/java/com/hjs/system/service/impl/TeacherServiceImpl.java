package com.hjs.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.TeacherMapper;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 20:37
 * @Modified By:
 */

@CacheConfig(cacheNames = "teacher") //redis库 "teacher"
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    /**
     * @Cacheable: 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存; 本方法执行后，先去缓存看有没有数据，如果没有，从数据库中查找出来，
     * 给缓存中存一份，返回结果，下次本方法执行，在缓存未过期情况下，先在缓存中查找，有的话直接返回，没有的话从数据库查找
     * 主要参数：
     * (1) value，缓存名称，至少指定一个；可通过 @CacheConfig 一次性声明
     * (2) key：可以为空，指定则必须满足SpEL表达式，不指定，则默认采用RedisConfig中重写的keyGenerator()方法生成的key：类名+方法名+参数列表(值)；
     * (3) condition：缓存条件，可为空，使用SpEL编写，返回true则进行缓存，false不缓存
     *
     * 区别：
     * @CachePut 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用，
     * 即每次不管缓存中有没有结果，都从数据库查找结果，并将结果更新到缓存，并返回结果
     * 主要参数：
     * (1) value
     * (2) key
     * (3) condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Cacheable
    @Override
    public Page<Teacher> findTeacherByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return teacherMapper.findAllTeacher();
    }


    @Override
    public Page<Teacher> fuzzyQueryTeacherByPage(String search, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return teacherMapper.fuzzyQueryAllTeacher(search);
    }


    @Cacheable
    @Override
    public Teacher findTeacherByTeacherId(String teacherId) {
        return teacherMapper.findTeacherByTeacherId(teacherId);
    }


    @Cacheable
    @Override
    public Teacher findTeacherByTid(Integer tid) {
        return teacherMapper.findTeacherByTid(tid);
    }


    /**
     * @CacheEvict: 主要针对方法配置，能够根据一定的条件对指定缓存进行清空：
     * 主要参数：
     * (1) value: 缓存的名称, 必须指定至少一个，@CacheConfig 一次性声明
     * (2) key：可以为空，指定则必须满足SpEL表达式，不指定，则默认采用RedisConfig中重写的keyGenerator()方法生成的key：类名+方法名+参数列表值；
     * (3) condition：缓存条件，可为空，使用SpEL编写，返回true则进行缓存，false不缓存
     * (4) allEntries：是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存
     * (5) beforeInvocation：是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存
     * @param teacher
     * @return
     */
    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateProfile(teacher);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updatePassword(Integer tid, String password) {
        return teacherMapper.updatePassword(tid, password);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int addTeacher(Teacher teacher) {
        return teacherMapper.insertTeacher(teacher);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteTeacherByTid(Integer tid) {
        return teacherMapper.deleteTeacherByTid(tid);
    }


}
