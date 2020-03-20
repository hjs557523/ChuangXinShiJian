package com.hjs.system.mapper;

import com.hjs.system.model.File;
import org.springframework.stereotype.Repository;


@Repository
public interface FileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);
}