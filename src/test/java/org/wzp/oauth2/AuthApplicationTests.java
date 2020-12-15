package org.wzp.oauth2;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.util.IOUtils;
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
import sun.nio.ch.IOUtil;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class AuthApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 查询
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


    @Autowired
    private UserMapper userMapper;

    @Test
    void selectPage() {
        Page<User> page = new Page<>(1, 20);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","普通");
        queryWrapper.select("id","name");
        queryWrapper.orderByDesc("name");
        queryWrapper.orderByAsc("id");
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        System.out.println(userIPage);
    }

    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page=new Page<>(1,20);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","普通");
        queryWrapper.like("name","南风");
        queryWrapper.select("id","name");
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, queryWrapper);
        System.out.println(mapIPage);
    }

    @Test
    void zip(){
        File file = ZipUtil.zip("G:\\wuliangye_game","G:\\wuliangye_game1",true);
        String filePath = file.getPath();
        System.out.println(filePath);
    }

}
