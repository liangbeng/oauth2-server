package org.wzp.oauth2.email;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wzp.oauth2.config.CustomConfig;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/20 10:06
 */
@Configuration
public class EmailConfig {


    /**
     * 交换机
     *
     * @return
     */
    @Bean
    public DirectExchange emailDirect() {
        return new DirectExchange(CustomConfig.emailExchange);
    }


    /**
     * 队列通过路由键绑定到交换机上
     *
     * @param emailDirectQueue
     * @param emailDirect
     * @return
     */
    @Bean
    public Binding emailDirectBinding(Queue emailDirectQueue, DirectExchange emailDirect) {
        return BindingBuilder.bind(emailDirectQueue).to(emailDirect).with(CustomConfig.routingKey);
    }


    /**
     * 路由队列并持久化
     *
     * @return
     */
    @Bean
    public Queue emailDirectQueue() {
        return new Queue("emailDirectQueue",true);
//        return new AnonymousQueue();
    }


    /**
     * 生产者
     *
     * @return
     */
    @Bean
    public EmailProducer emailProducer() {
        return new EmailProducer();
    }


    /**
     * 消费者
     *
     * @return
     */
    @Bean
    public EmailConsumer emailConsumer() {
        return new EmailConsumer();
    }
}
