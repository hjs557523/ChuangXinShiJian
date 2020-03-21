package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.Role;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:20
 * @Modified By:
 */
public interface RoleService {

    Page<Role> findRoleByPage(int pageNo, int pageSize);

    int deleteRoleByRid(Integer rid);

    int insertRole(Role record);

    Role findRoleByRid(Integer rid);

    int updateRole(Role record);

}
