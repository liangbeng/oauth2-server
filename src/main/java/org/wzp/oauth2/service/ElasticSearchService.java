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

    JSONObject getJsonObject(HashMap<String, String> map, SearchSourceBuilder searchSourceBuilder, BoolQueryBuilder boolQueryBuilder, String document) throws IOException;
}
