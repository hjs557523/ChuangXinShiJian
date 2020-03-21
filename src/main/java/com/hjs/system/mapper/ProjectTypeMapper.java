package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.ProjectType;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectTypeMapper {

    Page<ProjectType> findAllProjectType();

    int deleteProjectTypeByPid(Integer pid);

    int insertProjectType(ProjectType record);

    ProjectType findProjectTypeByPid(Integer pid);

    int updateProjectType(ProjectType record);
}