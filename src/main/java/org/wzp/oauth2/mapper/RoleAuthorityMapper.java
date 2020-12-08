package org.wzp.oauth2.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.wzp.oauth2.entity.RoleAuthority;

import java.util.List;

public interface RoleAuthorityMapper {

    @CacheEvict(value = "RoleAuthorityList", allEntries = true)
    int deleteByPrimaryKey(Long id);

    @CacheEvict(value = "roleAuthorityList", allEntries = true)
    int deleteByRoleId(Long roleId);

    @CacheEvict(value = "roleAuthorityList", allEntries = true)
    int insert(RoleAuthority record);

    @CacheEvict(value = "roleAuthorityList", allEntries = true)
    int insertSelective(RoleAuthority record);

    @Cacheable(value = "roleAuthorityList", unless = "#result == null")
    RoleAuthority selectByPrimaryKey(Long id);

    @CacheEvict(value = "roleAuthorityList", allEntries = true)
    int updateByPrimaryKeySelective(RoleAuthority record);

    @CacheEvict(value = "roleAuthorityList", allEntries = true)
    int updateByPrimaryKey(RoleAuthority record);

    @Cacheable(value = "roleAuthorityList", unless = "#result == null")
    List<RoleAuthority> selectByRoleId(Long roleId);

    @Cacheable(value = "roleAuthorityList", unless = "#result == null")
    List<RoleAuthority> selectByAuthorityId(Long authorityId);

}