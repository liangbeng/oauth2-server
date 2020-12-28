package org.wzp.oauth2.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.config.CustomConfig;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.entity.EmailMsgLog;
import org.wzp.oauth2.mapper.EmailMsgLogMapper;
import org.wzp.oauth2.service.ElasticSearchService;
import org.wzp.oauth2.util.ObjUtil;
import org.wzp.oauth2.email.EmailProducer;
import org.wzp.oauth2.vo.EmailMsgLogVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/19 9:47
 */
@Api(tags = "email管理")
@Slf4j
@RestController
@RequestMapping("/back/email")
public class EmailController extends BaseConfig {

    @Resource
    private EmailMsgLogMapper emailMsgLogMapper;
    @Resource
    private EmailProducer emailProducer;
    @Resource
    private ElasticSearchService elasticSearchService;

    private final String emailExchange = CustomConfig.emailExchange;
    private final String routingKey = CustomConfig.routingKey;


    @ApiOperation("发送邮件")
    @ApiOperationSupport(ignoreParameters = {"id", "updatedTime"})
    @PostMapping("/sendEmail")
    public Result sendEmail(@RequestBody EmailMsgLogVO emailMsgLogVO) {
        //先添加记录，再发邮件，避免出现消息已发后出现异常导致未添加记录
        EmailMsgLog emailMsgLog = new EmailMsgLog();
        emailMsgLog.setTitle(emailMsgLogVO.getTitle());
        emailMsgLog.setEmail(emailMsgLogVO.getEmail());
        emailMsgLog.setContent(emailMsgLogVO.getContent());
        emailMsgLog.setEmailEnum(emailMsgLogVO.getEmailEnum().getValue());
        emailMsgLog.setExchange(emailExchange);
        emailMsgLog.setRoutingKey(routingKey);
        emailMsgLogMapper.insertSelective(emailMsgLog);
        emailMsgLogVO.setId(emailMsgLog.getId());
        //添加到rabbit队列中
        emailProducer.sendEmail(emailMsgLogVO);
        return Result.ok();
    }


    @ApiOperation("邮件日志查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query", example = "xxxx"),
            @ApiImplicitParam(name = "email", value = "邮件接收方", dataType = "string", paramType = "query", example = "xxxx@163.com"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "page", value = "页数，从0开始", dataType = "int", paramType = "query", example = "0"),
            @ApiImplicitParam(name = "sort", value = "排序规则，可传入多个sort参数", dataType = "string", paramType = "query", example = "createdAt desc")
    })
    @PostMapping("/findAll")
    public Result findAll(@RequestBody HashMap<String, String> map) throws IOException {
        //指定查询文档
        String document = "email_msg_log";
        //构建查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (!ObjUtil.isEmpty(map.get("title"))) {
            String title = String.valueOf(map.get("title"));
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("title", "*" + title + "*"));
        }
        if (!ObjUtil.isEmpty(map.get("email"))) {
            String email = String.valueOf(map.get("email"));
            boolQueryBuilder.must(QueryBuilders.matchQuery("email", email));
        }
        //添加需要显示的字段，需要过滤的字段设置为null即可
        String[] includes = new String[]{"id", "createdTime", "updatedTime", "title", "email", "content", "emailEnum", "exchange", "routingKey", "status", "tryCount"};
        //第1个参数是需要显示的字段，第2个参数是需要过滤的字段
        searchSourceBuilder.fetchSource(includes, null);
        JSONObject jsonObject = elasticSearchService.getJsonObject(map, searchSourceBuilder, boolQueryBuilder, document);
        return Result.ok(jsonObject);
    }


}
