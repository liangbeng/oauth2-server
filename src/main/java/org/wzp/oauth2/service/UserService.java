package org.wzp.oauth2.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wzp.oauth2.entity.User;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/17 14:35
 */
public interface UserService extends IService<User> {

    IPage<User> getUserByPage(Integer current, Integer size);
}
