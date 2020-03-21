package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.Group;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupMapper {

    Page<Group> findAllGroup();

    Page<Group> findGroupByTid(Integer tid);

    Page<Group> findGroupByStateId(Integer stateId);

    Page<Group> findGroupByOwnerId(Integer ownerId);

    Page<Group> findGroupBySubjectId(Integer subjectId);

    int deleteGroupByGid(Integer gid);

    int insertGroup(Group record);

    Group findGroupByGid(Integer gid);

    int updateGroup(Group record);

}