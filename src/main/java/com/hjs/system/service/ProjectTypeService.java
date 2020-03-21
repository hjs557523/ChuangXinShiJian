package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.ProjectType;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:25
 * @Modified By:
 */
public interface ProjectTypeService {

    Page<ProjectType> findProjectTypeByPage(int pageNo, int pageSize);

    int deleteProjectTypeByPid(Integer pid);

    int insertProjectType(ProjectType record);

    ProjectType findProjectTypeByPid(Integer pid);

    int updateProjectType(ProjectType record);

}
