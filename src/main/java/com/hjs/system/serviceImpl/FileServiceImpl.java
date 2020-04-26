package com.hjs.system.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjs.system.mapper.FileMapper;
import com.hjs.system.model.File;
import com.hjs.system.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/22 11:01
 * @Modified By:
 */

@CacheConfig(cacheNames = "file")
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;


    @Cacheable
    @Override
    public Page<File> findFileByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return fileMapper.findAllFile();
    }


    @Cacheable
    @Override
    public Page<File> findFileByGroupID(Integer groupId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return fileMapper.findFileByGroupID(groupId);
    }


    @Cacheable
    @Override
    public Page<File> findFileByAuthorId(Integer authorId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return fileMapper.findFileByAuthorId(authorId);
    }


    @Cacheable
    @Override
    public Page<File> findFileByFileType(Integer fileType, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return fileMapper.findFileByFileType(fileType);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int deleteFileByFileId(Integer fileId) {
        return fileMapper.deleteFileByFileId(fileId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int insertFile(File record) {
        return fileMapper.insertFile(record);
    }


    @Cacheable
    @Override
    public File findFileByFileId(Integer fileId) {
        return fileMapper.findFileByFileId(fileId);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public int updateFile(File record) {
        return fileMapper.updateFile(record);
    }

}
