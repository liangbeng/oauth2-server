package org.wzp.oauth2.rabbitMq.fanout;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布订阅模式
 *
 * @Author: zp.wei
 * @DATE: 2020/11/17 13:10
 */
@Configuration
public class FanoutRabbitConfig {

    /**
     * 交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("exchange.fanout");
    }

    /**
     * 匿名队列
     *
     * @return
     */
    @Bean
    public Queue fanoutQueue1() {
        return new AnonymousQueue();
    }

    /**
     * 匿名队列
     *
     * @return
     */
    @Bean
    public Queue fanoutQueue2() {
        return new AnonymousQueue();
    }

    /**
     * 匿名队列绑定到交换机
     *
     * @param fanout
     * @param fanoutQueue1
     * @return
     */
    @Bean
    public Binding fanoutBinding1(FanoutExchange fanout, Queue fanoutQueue1) {
        return BindingBuilder.bind(fanoutQueue1).to(fanout);
    }

    /**
     * 匿名队列绑定到交换机
     *
     * @param fanout
     * @param fanoutQueue2
     * @return
     */
    @Bean
    public Binding fanoutBinding2(FanoutExchange fanout, Queue fanoutQueue2) {
        return BindingBuilder.bind(fanoutQueue2).to(fanout);
    }

    /**
     * 消费者
     *
     * @return
     */
    @Bean
    public FanoutConsumer fanoutConsumer() {
        return new FanoutConsumer();
    }

    /**
     * 生产者
     *
     * @return
     */
    @Bean
    public FanoutProducer fanoutProducer() {
        return new FanoutProducer();
    }


}
