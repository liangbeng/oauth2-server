package org.wzp.oauth2.mapper;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.wzp.oauth2.entity.LoginLog;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/11 10:50
 */
@Repository
public interface LoginLogRepository extends ElasticsearchRepository<LoginLog, Long> {
}
