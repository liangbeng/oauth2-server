package org.wzp.oauth2.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.service.ElasticSearchService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/17 10:48
 */
@Service
public class ElasticSearchServiceImpl extends BaseConfig implements ElasticSearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;


    @Override
    public JSONObject getJsonObject(HashMap<String, String> map, SearchSourceBuilder searchSourceBuilder, BoolQueryBuilder boolQueryBuilder, String document) throws IOException {
        getPageSort(map);
        Integer page = Integer.valueOf(map.get("page"));
        Integer size = Integer.valueOf(map.get("size"));
        String sortField = map.get("sortField");
        SortOrder sortOrder = SortOrder.valueOf(map.get("sortOrder"));
        // 封装查询条件
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.sort(new FieldSortBuilder(sortField).order(sortOrder));
        searchSourceBuilder.from(page * size);
        searchSourceBuilder.size(size);
        searchSourceBuilder.timeout(new TimeValue(2, TimeUnit.SECONDS));
        searchSourceBuilder.trackTotalHits(true);
        //构建查询器
        SearchRequest searchRequest = new SearchRequest(document);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Map> list = new ArrayList<>();
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit hit : searchHits.getHits()) {
            list.add(hit.getSourceAsMap());
        }
        Long totalSize = searchHits.getTotalHits().value;
        Long totalPage = totalSize % size > 0 ? (totalSize / size) + 1 : (totalSize / size);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("static", page);
        jsonObject.put("size", size);
        jsonObject.put("totalSize", totalSize);
        jsonObject.put("totalPage", totalPage);
        jsonObject.put("content", list);
        return jsonObject;
    }


}
