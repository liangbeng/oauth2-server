package org.wzp.oauth2.rabbitMq.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * 消费者
 *
 * @Author: zp.wei
 * @DATE: 2020/11/17 13:16
 */
@Slf4j
@RabbitListener(queues = "simple")
public class SimpleConsumer {

    /**
     * 简易模式
     *
     * @param in
     */
    @RabbitHandler
    public void receive(String in) {
        log.info(" [接收到消息] Received '{}'", in);
    }



}
