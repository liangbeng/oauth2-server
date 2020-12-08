package org.wzp.oauth2.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.wzp.oauth2.entity.Authority;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.mapper.AuthorityMapper;
import org.wzp.oauth2.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 验证用户
 *
 * @Author: zp.wei
 * @DATE: 2020/8/31 14:51
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 添加用户拥有的多个角色
        List<Authority> list = authorityMapper.findByUsername(username);
        if (!CollectionUtils.isEmpty(list)) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            list.forEach(authority -> {
                authorities.add(new SimpleGrantedAuthority(authority.getName()));
            });
            user.setAuthorities(authorities);
        }
        return user;
    }
}
