package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Group;
import com.hjs.system.model.GroupMember;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:31
 * @Modified By:
 */
public interface GroupService {

    Page<Group> findGroupByPage(int pageNo, int pageSize);

    Page<Group> findGroupByStateId(Integer stateId, int pageNo, int pageSize);

    Page<Group> findGroupByOwnerId(Integer ownerId, int pageNo, int pageSize);

    Page<Group> findGroupBySubjectId(Integer subjectId, int pageNo, int pageSize);

    int deleteGroupByGid(Integer gid);

    int insertGroup(Group record);

    Group findGroupByGid(Integer gid);

    int updateGroup(Group record);

    int createGroup(Group record);

}
