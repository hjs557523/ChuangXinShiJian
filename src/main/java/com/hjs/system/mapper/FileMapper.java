package com.hjs.system.mapper;

import com.hjs.system.model.File;
import org.springframework.stereotype.Repository;


@Repository
public interface FileMapper {

    int deleteFileByFileId(Integer fileId);

    int insertFile(File record);

    File findFileByFileId(Integer fileId);

    int updateFile(File record);

}