package com.hjs.system.mapper;

import com.hjs.system.model.Group;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupMapper {

    int deleteGroupByGid(Integer gid);

    int insertGroup(Group record);

    Group findGroupByGid(Integer gid);

    int updateGroup(Group record);

}