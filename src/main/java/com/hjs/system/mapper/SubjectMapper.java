package com.hjs.system.mapper;

import com.hjs.system.model.Subject;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectMapper {

    int deleteSubjectBySubjectId(Integer subjectId);

    int insertSubject(Subject record);

    Subject findSubjectBySubjectId(Integer subjectId);

    int updateSubject(Subject record);

}