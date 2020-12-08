package org.wzp.oauth2.rabbitMq.simple;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 简单模式
 *
 * @Author: zp.wei
 * @DATE: 2020/11/17 13:10
 */
@Configuration
public class SimpleRabbitConfig {

    /**
     * 消息队列
     *
     * @return
     */
    @Bean
    public Queue hello() {
        return new Queue("simple");
    }

    /**
     * 消息发送者
     *
     * @return
     */
    @Bean
    public SimpleProducer simpleProducer() {
        return new SimpleProducer();
    }

    /**
     * 消息接受者
     *
     * @return
     */
    @Bean
    public SimpleConsumer simpleConsumer() {
        return new SimpleConsumer();
    }


}
