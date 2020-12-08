package org.wzp.oauth2.rabbitMq.topic;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通配符模式
 *
 * @Author: zp.wei
 * @DATE: 2020/11/17 14:41
 */
@Configuration
public class TopicRabbitConfig {

    /**
     * 交换机
     *
     * @return
     */
    @Bean
    public TopicExchange topic() {
        return new TopicExchange("exchange.topic");
    }

    /**
     * 匿名消息队列
     *
     * @return
     */
    @Bean
    public Queue topicQueue1() {
        return new AnonymousQueue();
    }

    /**
     * 匿名消息队列
     *
     * @return
     */
    @Bean
    public Queue topicQueue2() {
        return new AnonymousQueue();
    }

    /**
     * 消息队列绑定交换机
     *
     * @param topic
     * @param topicQueue1
     * @return
     */
    @Bean
    public Binding topicBinding1a(TopicExchange topic, Queue topicQueue1) {
        return BindingBuilder.bind(topicQueue1).to(topic).with("*.orange.*");
    }

    /**
     * 消息队列绑定交换机
     *
     * @param topic
     * @param topicQueue1
     * @return
     */
    @Bean
    public Binding topicBinding1b(TopicExchange topic, Queue topicQueue1) {
        return BindingBuilder.bind(topicQueue1).to(topic).with("*.*.rabbit");
    }

    /**
     * 消息队列绑定交换机
     *
     * @param topic
     * @param topicQueue2
     * @return
     */
    @Bean
    public Binding topicBinding2a(TopicExchange topic, Queue topicQueue2) {
        return BindingBuilder.bind(topicQueue2).to(topic).with("lazy.#");
    }

    /**
     * 消费者
     *
     * @return
     */
    @Bean
    public TopicConsumer topicConsumer() {
        return new TopicConsumer();
    }

    /**
     * 生成者
     *
     * @return
     */
    @Bean
    public TopicProducer topicProducer() {
        return new TopicProducer();
    }
}
