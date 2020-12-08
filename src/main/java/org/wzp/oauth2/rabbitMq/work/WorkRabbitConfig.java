package org.wzp.oauth2.rabbitMq.work;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工作模式
 * @Author: zp.wei
 * @DATE: 2020/11/17 13:10
 */
@Configuration
public class WorkRabbitConfig {

    /**
     * 消息队列
     *
     * @return
     */
    @Bean
    public Queue work() {
        return new Queue("work");
    }

    /**
     * 消息发送者
     *
     * @return
     */
    @Bean
    public WorkProducer workProducer() {
        return new WorkProducer();
    }

    /**
     * 消息接受者
     *
     * @return
     */
    @Bean
    public WorkConsumer workConsumer() {
        return new WorkConsumer(1);
    }

    /**
     * 消息接受者
     *
     * @return
     */
    @Bean
    public WorkConsumer1 workConsumer1() {
        return new WorkConsumer1(2);
    }


}
