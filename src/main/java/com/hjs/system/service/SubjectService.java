package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Subject;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:19
 * @Modified By:
 */
public interface SubjectService {

    Page<Subject> findSubjectByPage(int pageNo, int pageSize);

    Page<Subject> findSubjectByTid(Integer tid, int pageNo, int pageSize);

    Page<Subject> findSubjectByTname(int pageNo, int pageSize, String name);

    int deleteSubjectBySubjectId(Integer subjectId);

    int insertSubject(Subject record);

    Subject findSubjectBySubjectId(Integer subjectId);

    int updateSubject(Subject record);

}
