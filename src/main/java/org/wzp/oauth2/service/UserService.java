package org.wzp.oauth2.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wzp.oauth2.entity.User;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/17 14:35
 */
public interface UserService {

    IPage<User> getUserByPage(Integer current, Integer size);
}
