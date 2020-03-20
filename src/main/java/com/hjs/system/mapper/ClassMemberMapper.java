package com.hjs.system.mapper;

import com.hjs.system.model.ClassMember;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassMemberMapper {

    int deleteClassMemberByClassMemberId(Integer classMemberId);

    int insertClassMember(ClassMember record);

    ClassMember findClassMemberByClassMemberId(Integer classMemberId);

    int updateClassMember(ClassMember record);

}