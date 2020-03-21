package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.ClassMember;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:34
 * @Modified By:
 */
public interface ClassMemberService {

    Page<ClassMember> findClassMemberByPage(int pageNo, int pageSize);

    Page<ClassMember> findClassMemberByClassId(Integer classId, int pageNo, int pageSize);

    Page<ClassMember> findClassMemberByStudentId(Integer studentId, int pageNo, int pageSize);

    int deleteClassMemberByClassMemberId(Integer classMemberId);

    int insertClassMember(ClassMember record);

    ClassMember findClassMemberByClassMemberId(Integer classMemberId);

    int updateClassMember(ClassMember record);

}
