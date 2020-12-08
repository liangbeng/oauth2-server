package org.wzp.oauth2.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.wzp.oauth2.entity.UserRole;

import java.util.List;

public interface UserRoleMapper {

    @CacheEvict(value = "userRoleList", allEntries = true)
    int deleteByPrimaryKey(Long id);

    @CacheEvict(value = "userRoleList", allEntries = true)
    int deleteByUserId(Long userId);

    @CacheEvict(value = "userRoleList", allEntries = true)
    int insert(UserRole record);

    @CacheEvict(value = "userRoleList", allEntries = true)
    int insertSelective(UserRole record);

    @Cacheable(value = "userRoleList", unless = "#result == null")
    UserRole selectByPrimaryKey(Long id);

    @CacheEvict(value = "userRoleList", allEntries = true)
    int updateByPrimaryKeySelective(UserRole record);

    @CacheEvict(value = "userRoleList", allEntries = true)
    int updateByPrimaryKey(UserRole record);

    @Cacheable(value = "userRoleList", unless = "#result == null")
    List<UserRole> selectByUserId(Long userId);
}