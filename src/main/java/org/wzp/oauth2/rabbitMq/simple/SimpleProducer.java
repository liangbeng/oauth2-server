package org.wzp.oauth2.rabbitMq.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;

/**
 * 生产者
 *
 * @Author: zp.wei
 * @DATE: 2020/11/17 13:14
 */
@Slf4j
public class SimpleProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final String queueName = "simple";

    /**
     * 简单模式
     */
    public void send() {
        String message = "Hello World!";
        this.rabbitTemplate.convertAndSend(queueName, message);
        log.info(" [简单模式发送消息] Sent '{}'", message);
    }

}
