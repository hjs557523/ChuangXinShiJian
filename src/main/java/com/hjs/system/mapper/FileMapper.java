package com.hjs.system.mapper;

import com.github.pagehelper.Page;
import com.hjs.system.model.File;
import org.springframework.stereotype.Repository;


@Repository
public interface FileMapper {

    Page<File> findAllFile();

    Page<File> findFileByGroupID(Integer groupId);

    Page<File> findFileByAuthorId(Integer authorId);

    Page<File> findFileByFileType(Integer fileType);

    int deleteFileByFileId(Integer fileId);

    int insertFile(File record);

    File findFileByFileId(Integer fileId);

    int updateFile(File record);

}