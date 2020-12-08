package org.wzp.oauth2.rabbitMq.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由模式
 *
 * @Author: zp.wei
 * @DATE: 2020/11/17 14:24
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 交换机
     *
     * @return
     */
    @Bean
    public DirectExchange direct() {
        return new DirectExchange("exchange.direct");
    }


    /**
     * 路由键
     *
     * @param direct
     * @param directQueue1
     * @return
     */
    @Bean
    public Binding directBinding1a(DirectExchange direct, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(direct).with("orange");
    }

    /**
     * 路由键
     *
     * @param direct
     * @param directQueue1
     * @return
     */
    @Bean
    public Binding directBinding1b(DirectExchange direct, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(direct).with("black");
    }

    /**
     * 路由键
     *
     * @param direct
     * @param directQueue2
     * @return
     */
    @Bean
    public Binding directBinding2a(DirectExchange direct, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(direct).with("green");
    }

    /**
     * 路由键
     *
     * @param direct
     * @param directQueue2
     * @return
     */
    @Bean
    public Binding directBinding2b(DirectExchange direct, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(direct).with("black");
    }

    /**
     * 匿名路由队列
     *
     * @return
     */
    @Bean
    public Queue directQueue1() {
        return new AnonymousQueue();
    }

    /**
     * 匿名路由队列
     *
     * @return
     */
    @Bean
    public Queue directQueue2() {
        return new AnonymousQueue();
    }

    /**
     * 生产者
     *
     * @return
     */
    @Bean
    public DirectProducer directProducer() {
        return new DirectProducer();
    }

    /**
     * 消费者
     *
     * @return
     */
    @Bean
    public DirectConsumer directConsumer() {
        return new DirectConsumer();
    }


}
