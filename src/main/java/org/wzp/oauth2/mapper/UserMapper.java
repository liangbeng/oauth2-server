package org.wzp.oauth2.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.wzp.oauth2.entity.User;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {

    @CacheEvict(value = "userList", allEntries = true)
    int deleteByPrimaryKey(Long id);

    @CacheEvict(value = "userList", allEntries = true)
    int insert(User record);

    @CacheEvict(value = "userList", allEntries = true)
    int insertSelective(User user);

    @Cacheable(value = "userList", unless = "#result == null")
    User selectByPrimaryKey(Long id);

    User selectByUsername(String username);

    @CacheEvict(value = "userList", allEntries = true)
    int updateByPrimaryKeySelective(User user);

    @CacheEvict(value = "userList", allEntries = true)
    int updateByPrimaryKey(User user);

    List<User> findAllBySome(HashMap map);


}