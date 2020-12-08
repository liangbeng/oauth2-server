package org.wzp.oauth2.controller.back;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.*;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.entity.LoginLog;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.enumeration.LoginLogEnum;
import org.wzp.oauth2.mapper.LoginLogMapper;
import org.wzp.oauth2.service.ElasticSearchService;
import org.wzp.oauth2.util.IpUtil;
import org.wzp.oauth2.util.StringUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/12 10:30
 */
@Api(tags = "登录日志管理")
@Slf4j
@RestController
@RequestMapping("/back/loginLog")
public class LoginLogController extends BaseConfig {

    @Resource
    private LoginLogMapper loginLogMapper;
    @Resource
    private ElasticSearchService elasticSearchService;


    @ApiOperation("新增账户登录日志")
    @GetMapping("/saveIn")
    public Result saveIn() {
        User user = getUser();
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setUsername(user.getUsername());
        loginLog.setDetail("用户" + user.getUsername() + "登录");
        loginLog.setLoginIp(IpUtil.getRealIp(request));
        loginLog.setLoginLogEnum(LoginLogEnum.LOGIN.getValue());
        loginLogMapper.insertSelective(loginLog);
        return Result.ok();
    }


    @ApiOperation("新增账户退出日志")
    @GetMapping("/saveOut")
    public Result saveOut() {
        User user = getUser();
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setUsername(user.getUsername());
        loginLog.setDetail("用户" + user.getUsername() + "退出登录");
        loginLog.setLoginIp(IpUtil.getRealIp(request));
        loginLog.setLoginLogEnum(LoginLogEnum.LOGIN_OUT.getValue());
        loginLogMapper.insertSelective(loginLog);
        return Result.ok();
    }


    @ApiOperation("登录日志查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query", example = "xxxx"),
            @ApiImplicitParam(name = "loginLogEnum", value = "日志类型", dataType = "string", paramType = "query", example = "LOGIN"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "page", value = "页数，从0开始", dataType = "int", paramType = "query", example = "0"),
            @ApiImplicitParam(name = "sort", value = "排序规则，可传入多个sort参数", dataType = "string", paramType = "query", example = "createdAt desc")
    })
    @PostMapping("/findAll")
    public Result findAll(@RequestBody HashMap<String, String> map) throws IOException {
        //指定查询文档
        String document = "login_log";
        //构建查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (!StringUtil.isEmpty(map.get("username"))) {
            String username = String.valueOf(map.get("username"));
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("username", "*" + username + "*"));
        }
        if (!StringUtil.isEmpty(map.get("loginLogEnum"))) {
            String loginLogEnum = String.valueOf(map.get("loginLogEnum"));
            boolQueryBuilder.must(QueryBuilders.matchQuery("loginLogEnum", loginLogEnum));
        }
        //添加需要显示的字段，需要过滤的字段设置为null即可
        String[] includes = new String[]{"id", "createdTime", "updatedTime", "userId", "username", "detail", "loginIp", "loginLogEnum"};
        //第1个参数是需要显示的字段，第2个参数是需要过滤的字段
        searchSourceBuilder.fetchSource(includes, null);
        JSONObject jsonObject = elasticSearchService.getJsonObject(map, searchSourceBuilder, boolQueryBuilder, document);
        return Result.ok(jsonObject);
    }


}
