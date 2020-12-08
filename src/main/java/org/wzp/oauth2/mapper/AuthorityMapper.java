package org.wzp.oauth2.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.wzp.oauth2.entity.Authority;

import java.util.HashMap;
import java.util.List;

public interface AuthorityMapper {

    @CacheEvict(value = "authorityList", allEntries = true)
    int deleteByPrimaryKey(Long id);

    @CacheEvict(value = "authorityList", allEntries = true)
    int insert(Authority record);

    @CacheEvict(value = "authorityList", allEntries = true)
    int insertSelective(Authority record);

    @Cacheable(value = "authorityList", unless = "#result == null")
    Authority selectByPrimaryKey(Long id);

    @CacheEvict(value = "authorityList", allEntries = true)
    int updateByPrimaryKeySelective(Authority record);

    @CacheEvict(value = "authorityList", allEntries = true)
    int updateByPrimaryKey(Authority record);

    @Select("SELECT a.id,a.name from user u,user_role ur, role r, role_authority ra, authority a where " +
            "u.username=#{username} and u.id = ur.user_id and ur.role_id = r.id and r.id = ra.role_id and ra.authority_id = a.id")
    @Cacheable(value = "authorityList", unless = "#result == null")
    List<Authority> findByUsername(String username);

    List<Authority> findAllBySome(HashMap map);
}