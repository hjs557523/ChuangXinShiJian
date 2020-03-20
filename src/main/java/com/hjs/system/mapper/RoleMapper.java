package com.hjs.system.mapper;

import com.hjs.system.model.Role;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleMapper {

    int deleteRoleByRid(Integer rid);

    int insertRole(Role record);

    Role findRoleByRid(Integer rid);

    int updateRole(Role record);
}