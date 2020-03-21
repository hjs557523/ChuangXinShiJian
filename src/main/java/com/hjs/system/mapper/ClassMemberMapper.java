package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.ClassMember;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassMemberMapper {

    Page<ClassMember> findAllClassMember();

    Page<ClassMember> findClassMemberByClassId(Integer classId);

    Page<ClassMember> findClassMemberByStudentId(Integer studentId);

    int deleteClassMemberByClassMemberId(Integer classMemberId);

    int insertClassMember(ClassMember record);

    ClassMember findClassMemberByClassMemberId(Integer classMemberId);

    int updateClassMember(ClassMember record);

}