package org.wzp.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@EnableCaching  //开启缓存
@SpringBootApplication
@MapperScan("org.wzp.oauth2.mapper")//添加Mapper所在的包路径 否则无法加载 Mppaer bean
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    // 创建ThreadPoolTaskScheduler线程池
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.initialize();
        //设置线程池容量
        executor.setPoolSize(5);
        //线程名前缀
        executor.setThreadNamePrefix("dynamicTask-");
        //当调度器shutdown被调用时等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //线程关闭的等待时长
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

}
