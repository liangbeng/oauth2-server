package org.wzp.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.mapper.UserMapper;
import org.wzp.oauth2.service.UserService;

import javax.annotation.Resource;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/17 14:36
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public IPage<User> getUserByPage(Integer current, Integer size) {
        Page<User> page = new Page<>(current, size);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "username", "password");
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        return userIPage;
    }

}
