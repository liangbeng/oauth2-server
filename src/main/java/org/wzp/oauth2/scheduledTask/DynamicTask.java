package org.wzp.oauth2.scheduledTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.wzp.oauth2.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/30 11:40
 */
@Component
public class DynamicTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public Map<String, ScheduledFuture<?>> taskMap = new HashMap<>();


    /**
     * 初始化任务 在服务器重启后自动启动任务
     */
//    @PostConstruct
    public void initDynamic() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        String cron = "*/5 * * * * ?";
        list.forEach(s -> {
            ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(getRunnable(s), new CronTrigger(cron));
            taskMap.put(s, schedule);
        });
    }


    /**
     * 添加任务
     *
     * @param name
     * @param cron
     * @return
     */
    public boolean add(String name, String cron) {
        if (null != taskMap.get(name)) {
            return false;
        }
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(getRunnable(name), new CronTrigger(cron));
        taskMap.put(name, schedule);
        return true;
    }


    /**
     * 停止任务
     *
     * @param name
     * @return
     */
    public boolean stop(String name) {
        if (null == taskMap.get(name)) {
            return false;
        }
        ScheduledFuture<?> scheduledFuture = taskMap.get(name);
        scheduledFuture.cancel(true);
        taskMap.remove(name);
        return true;
    }


    public Runnable getRunnable(String name) {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println(name + "---动态定时任务运行---" + DateUtil.formatLocalDateTime());
            }
        };
    }


}
