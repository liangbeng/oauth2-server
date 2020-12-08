package org.wzp.oauth2.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;
import org.wzp.oauth2.config.CustomConfig;
import org.wzp.oauth2.service.RedisService;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/30 18:00
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;


    /**
     * 删除key值相同的缓存
     *
     * @param key
     */
    @Override
    public void delAllByKey(String key) {
        Set<String> keys = redisTemplate.keys(key + ":*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }


    /**
     * 根据key删除缓存
     *
     * @param keys
     */
    @Override
    public void delAllByKeys(List<String> keys) {
        if (keys != null && !keys.isEmpty()) {
            final Set<String>[] set = new Set[]{new HashSet<>()};
            keys.forEach(key -> {
                set[0].addAll(redisTemplate.keys(key));
            });
            if (set[0] != null && !set[0].isEmpty()) {
                redisTemplate.delete(set[0]);
            }
        }
    }


    /**
     * 根据key删除缓存
     *
     * @param key
     */
    @Override
    public void delByKey(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 根据username删除用户token
     *
     * @param username
     */
    @Override
    public void loginOut(String username) {
        String key = "uname_to_access:" + CustomConfig.withClient + ":" + username;
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) redisTemplate.opsForList().index(key, 0);
        if (defaultOAuth2AccessToken != null && defaultOAuth2AccessToken.getValue() != null) {
            consumerTokenServices.revokeToken(defaultOAuth2AccessToken.getValue());
        }
    }


    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 存储数据并设置过期时间
     *
     * @param key
     * @param val
     */
    @Override
    public void set(String key, Object val) {
        redisTemplate.opsForValue().set(key, val, 1, TimeUnit.DAYS);
    }


    /**
     * 根据key从redis中获取数据
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
