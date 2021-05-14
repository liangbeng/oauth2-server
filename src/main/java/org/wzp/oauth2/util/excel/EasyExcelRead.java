package org.wzp.oauth2.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.wzp.oauth2.config.SpringContextUtil;
import org.wzp.oauth2.entity.User;
import org.wzp.oauth2.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/23 9:41
 */
@Slf4j
public class EasyExcelRead extends AnalysisEventListener<User> {

    /**
     * 每隔3000条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int batchCount = 3000;

    List<User> list = new ArrayList<>();


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param user
     * @param context
     */
    @Override
    public void invoke(User user, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(user));
        list.add(user);
        // 达到batchCount了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= batchCount) {
            saveData();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 存储数据到数据库
     */
    private void saveData() {
        log.info("共有{}条数据，开始存储数据库！", list.size());
        UserMapper userMapper = SpringContextUtil.getBean(UserMapper.class);
        //不建议这样循环增加，如果是使用mybatis，则可自己写相关sql进行批量增加，如果是使用mybatis-plus，则其service提供saveBatch的批量新增功能
        list.forEach(userMapper::insertSelective);
//        UserService userService = SpringContextUtil.getBean(UserService.class);
//        userService.saveBatch(list);
        // 存储完成清理 list，避免数据重复
        list.clear();
        log.info("存储数据库成功！");
    }


}
