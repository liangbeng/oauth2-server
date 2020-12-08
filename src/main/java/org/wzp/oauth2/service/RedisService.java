package org.wzp.oauth2.service;

import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/30 17:59
 */
public interface RedisService {

    void delAllByKey(String key);

    void delAllByKeys(List<String> keys);

    void delByKey(String key);

    void loginOut(String username);

    Boolean hasKey(String key);

    void set(String key, Object val);

    Object get(String key);

}
