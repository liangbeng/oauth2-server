package org.wzp.oauth2;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.mapper.UserMapper;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class AuthApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**查询
     * SearchRequest 搜索请求
     * SearchSourceBuilder 条件构造
     * HighlightBuilder 构建高亮
     * TermQueryBuilder 精确查询
     * MatchAllQueryBuilder匹配所有
     * 查询条件，我们可以使用 QueryBuilders 工具来实现
     * QueryBuilders.termQuery 精确
     * QueryBuilders.matchAllQuery() 匹配所有
     */
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("book");
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.highlighter();
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "三国演义");
//        searchSourceBuilder.query(termQueryBuilder);
//        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "三国演义"));
        searchSourceBuilder.timeout(new TimeValue(2, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("=================================");
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }



}
