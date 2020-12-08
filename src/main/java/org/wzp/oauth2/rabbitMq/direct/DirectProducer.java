package org.wzp.oauth2.rabbitMq.direct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/17 14:26
 */
@Slf4j
public class DirectProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final String exchangeName = "exchange.direct";

    private final String[] keys = {"orange", "black", "green"};

    public void send(int index) {
        StringBuilder builder = new StringBuilder("Hello to ");
        int limitIndex = index % 3;
        String key = keys[limitIndex];
        builder.append(key).append(' ');
        builder.append(index + 1);
        String message = builder.toString();
        rabbitTemplate.convertAndSend(exchangeName, key, message);
        log.info(" [路由模式生产者发送消息] Sent '{}'", message);
    }
}
