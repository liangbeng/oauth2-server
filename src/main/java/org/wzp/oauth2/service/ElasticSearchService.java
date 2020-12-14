package org.wzp.oauth2.service;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/17 10:48
 */
public interface ElasticSearchService {

    /**
     * 查询数据
     *
     * @param map 分页构造器
     * @param searchSourceBuilder 字段过滤
     * @param boolQueryBuilder 查询条件构造器
     * @param document 查询文档
     * @return JSONObject
     * @throws IOException 异常抛出
     */
    JSONObject getJsonObject(HashMap<String, String> map, SearchSourceBuilder searchSourceBuilder, BoolQueryBuilder boolQueryBuilder, String document) throws IOException;
}
