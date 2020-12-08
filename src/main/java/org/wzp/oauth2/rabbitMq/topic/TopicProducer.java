package org.wzp.oauth2.rabbitMq.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/17 14:42
 */
@Slf4j
public class TopicProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final String exchangeName = "exchange.topic";

    private final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    public void send(int index) {
        StringBuilder builder = new StringBuilder("Hello to ");
        int limitIndex = index%keys.length;
        String key = keys[limitIndex];
        builder.append(key).append(' ');
        builder.append(index+1);
        String message = builder.toString();
        rabbitTemplate.convertAndSend(exchangeName, key, message);
        log.info(" [通配符模式发送消息] Sent '{}'",message);
    }
}
