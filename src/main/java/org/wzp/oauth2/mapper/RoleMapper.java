package org.wzp.oauth2.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.wzp.oauth2.entity.Role;

import java.util.HashMap;
import java.util.List;

public interface RoleMapper {

    @CacheEvict(value = "roleList", allEntries = true)
    int deleteByPrimaryKey(Long id);

    @CacheEvict(value = "roleList", allEntries = true)
    int insert(Role record);

    @CacheEvict(value = "roleList", allEntries = true)
    int insertSelective(Role record);

    @Cacheable(value = "roleList", unless = "#result == null")
    Role selectByPrimaryKey(Long id);

    @CacheEvict(value = "roleList", allEntries = true)
    int updateByPrimaryKeySelective(Role record);

    @CacheEvict(value = "roleList", allEntries = true)
    int updateByPrimaryKey(Role record);

    List<Role> findAllBySome(HashMap map);
}