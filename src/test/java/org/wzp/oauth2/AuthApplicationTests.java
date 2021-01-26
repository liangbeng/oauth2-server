package org.wzp.oauth2;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.mapper.UserMapper;
import org.wzp.oauth2.util.ObjUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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


    @Test
    void testDeleteRequest() throws IOException {
        //单挑数据删除
//        DeleteRequest request = new DeleteRequest("operate_log", "1120");
//        request.timeout("1s");
//        DeleteResponse response= restHighLevelClient.delete(request, RequestOptions.DEFAULT);
//        System.out.println(response.status());

        //执行批量操作的请求
        List<Integer> keys = new CopyOnWriteArrayList<>();
        keys.add(1);
        keys.add(2);
        keys.add(3);
        keys.add(4);
        keys.add(5);
        String[] ids = new String[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            ids[i] = keys.get(i).toString();
        }
        //客户端执行批量操作
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("operate_log");
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.idsQuery().addIds(ids));
        deleteByQueryRequest.setQuery(boolQueryBuilder);
        restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
    }


    @Autowired
    private UserMapper userMapper;

    @Test
    void selectPage() {
        Page<User> page = new Page<>(1, 20);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "普通");
        queryWrapper.select("id", "name");
        queryWrapper.orderByDesc("name");
        queryWrapper.orderByAsc("id");
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        System.out.println(userIPage);
    }

    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page = new Page<>(1, 20);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "普通");
        queryWrapper.like("name", "南风");
        queryWrapper.select("id", "name");
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, queryWrapper);
        System.out.println(mapIPage);
    }


    @Test
    void copyOnWriteArrayList() {
        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        for (Integer i : list) {
            if (i.equals(3)) {
                list.remove(i);
            }
            if (!list.contains(5)) {
                list.add(5);
            }
        }
        System.out.println(list);
    }

    @Test
    void subString(){
        String a = "1234567890";
        String b = ObjUtil.subString(a,5);
        System.out.println(b);
    }


}
