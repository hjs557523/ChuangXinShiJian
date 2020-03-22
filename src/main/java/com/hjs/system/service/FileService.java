package com.hjs.system.service;

import com.github.pagehelper.Page;
import com.hjs.system.model.File;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/20 17:33
 * @Modified By:
 */
public interface FileService {

    Page<File> findFileByPage(int pageNo, int pageSize);

    Page<File> findFileByGroupID(Integer groupId, int pageNo, int pageSize);

    Page<File> findFileByAuthorId(Integer authorId, int pageNo, int pageSize);

    Page<File> findFileByFileType(Integer fileType, int pageNo, int pageSize);

    int deleteFileByFileId(Integer fileId);

    int insertFile(File record);

    File findFileByFileId(Integer fileId);

    int updateFile(File record);

}
