package org.wzp.oauth2.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.wzp.oauth2.entity.FileList;

import java.util.HashMap;
import java.util.List;

public interface FileListMapper {

    @CacheEvict(value = "fileList", allEntries = true)
    int deleteByPrimaryKey(Long id);

    @CacheEvict(value = "fileList", allEntries = true)
    int insert(FileList record);

    @CacheEvict(value = "fileList", allEntries = true)
    int insertSelective(FileList record);

    @Cacheable(value = "fileList", unless = "#result == null")
    FileList selectByPrimaryKey(Long id);

    @CacheEvict(value = "fileList", allEntries = true)
    int updateByPrimaryKeySelective(FileList record);

    @CacheEvict(value = "fileList", allEntries = true)
    int updateByPrimaryKey(FileList record);

    List<FileList> findAllBySome(HashMap map);


}