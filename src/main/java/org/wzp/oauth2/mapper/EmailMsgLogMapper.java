package org.wzp.oauth2.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.wzp.oauth2.entity.EmailMsgLog;

public interface EmailMsgLogMapper {

    @CacheEvict(value = "emailMsgLogList", allEntries = true)
    int deleteByPrimaryKey(Long id);

    @CacheEvict(value = "emailMsgLogList", allEntries = true)
    int insert(EmailMsgLog record);

    @CacheEvict(value = "emailMsgLogList", allEntries = true)
    int insertSelective(EmailMsgLog record);

    @Cacheable(value = "emailMsgLogList", unless = "#result == null")
    EmailMsgLog selectByPrimaryKey(Long id);

    @CacheEvict(value = "emailMsgLogList", allEntries = true)
    int updateByPrimaryKeySelective(EmailMsgLog record);

    @CacheEvict(value = "emailMsgLogList", allEntries = true)
    int updateByPrimaryKey(EmailMsgLog record);
}