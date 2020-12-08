package org.wzp.oauth2.config;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.mapper.UserMapper;
import org.wzp.oauth2.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/31 14:23
 */
@Slf4j
public class BaseConfig {

    @Resource
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Resource
    private TokenEndpoint tokenEndpoint;
    @Resource
    public RestTemplate restTemplate;
    @Resource
    public RedisTemplate redisTemplate;
    @Resource
    private UserMapper userMapper;


    /**
     * 获取token
     *
     * @param username
     * @param password
     * @return
     */
    public Map getToken(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("client_secret", CustomConfig.secret);
        map.add("client_id", CustomConfig.withClient);
        map.add("grant_type", "password");
        try {
            Map<String, String> responseEntity = restTemplate.postForObject(CustomConfig.tokenUrl + "/oauth/token", map, Map.class);
            System.out.println(responseEntity);
            return responseEntity;
        } catch (Exception e) {
            System.out.println("授权失败");
            return null;
        }
    }


    /**
     * 获取token
     *
     * @param username
     * @param password
     * @return
     */
    public ResponseEntity getToken1(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("grant_type", "password");
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(CustomConfig.withClient, CustomConfig.secret, null);
            ResponseEntity<OAuth2AccessToken> responseEntity = tokenEndpoint.postAccessToken(authentication, map);
            System.out.println(responseEntity.getBody());
            return responseEntity;
        } catch (Exception e) {
            System.out.println("授权失败");
            return null;
        }
    }


    /**
     * 从Security中获取用户信息
     *
     * @return
     */
    public UserDetails getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails;
        } else {
            System.err.println("获取用户信息失败");
            throw new AuthenticationServiceException("authentication not found");
        }
    }


    /**
     * 获取登录用户的用户名
     *
     * @return
     */
    protected String getUsername() {
        String username = getAuthentication().getUsername();
        if (username == null) {
            return null;
        }
        return username;
    }


    /**
     * 获取当前登录用户
     *
     * @return
     */
    protected User getUser() {
        String username = getUsername();
        if (username == null) {
            return null;
        }
        User user = userMapper.selectByUsername(username);
        return user;
    }


    /**
     * 移除token
     *
     * @param username
     */
    public void removeToken(String username) {
        String key = "uname_to_access:" + CustomConfig.withClient + ":" + username;
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) redisTemplate.opsForList().index(key, 0);
        if (defaultOAuth2AccessToken != null && defaultOAuth2AccessToken.getValue() != null) {
            consumerTokenServices.revokeToken(defaultOAuth2AccessToken.getValue());
        }
    }


    /**
     * body参数分页器对象
     *
     * @param map
     * @return
     */
    public void getPageRequest(Map<String, Object> map) {
        Integer page = 1;
        Integer size = 10;
        String sort = "id asc";
        if (StringUtil.isEmpty(map.get("page"))) {
            map.put("page", page);
        }
        if (StringUtil.isEmpty(map.get("size"))) {
            map.put("size", size);
        }
        if (StringUtil.isEmpty(map.get("sort"))) {
            map.put("sort", sort);
        }
        page = Integer.parseInt(String.valueOf(map.get("page")));
        size = Integer.parseInt(String.valueOf(map.get("size")));
        sort = String.valueOf(map.get("sort"));
        PageHelper.startPage(page, size).setOrderBy(sort);
    }


    /**
     * 用于elasticSearch搜索的分页查询
     *
     * @param map
     */
    public void getPageSort(Map<String, String> map) {
        Integer page = 0;
        Integer size = 10;
        String sortField = "id";
        SortOrder sortOrder = SortOrder.ASC;
        if (StringUtil.isEmpty(map.get("page"))) {
            map.put("page", String.valueOf(page));
        }
        if (StringUtil.isEmpty(map.get("size"))) {
            map.put("size", String.valueOf(size));
        }
        if (!StringUtil.isEmpty(map.get("sort"))) {
            String sort = map.get("sort");
            sortField = StringUtil.strPrefix(sort, " ", 0);
            String sortCollation = StringUtil.strSuffix(sort, " ", 1);
            if ("desc".equals(sortCollation)) {
                sortOrder = SortOrder.DESC;
            }
        }
        map.put("sortField", sortField);
        map.put("sortOrder", String.valueOf(sortOrder));
    }


    /**
     * 获取缓存数据的 key 值
     *
     * @param map
     * @return
     */
    public String getKey(String keyPrefix, HashMap<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        sb.append(keyPrefix + "::{page=" + map.get("page") + ",sort=" + map.get("sort") + ",size=" + map.get("size") + "}");
        return sb.toString();
    }

}
