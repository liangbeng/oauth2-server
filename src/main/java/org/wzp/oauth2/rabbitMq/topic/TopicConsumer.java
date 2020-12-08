package org.wzp.oauth2.rabbitMq.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/17 14:44
 */
@Slf4j
public class TopicConsumer {


    @RabbitListener(queues = "#{topicQueue1.name}")
    public void receive1(String in) {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{topicQueue2.name}")
    public void receive2(String in) {
        receive(in, 2);
    }

    public void receive(String in, int receiver) {
        log.info("instance {} [通配符模式消费者接收消息] Received '{}'", receiver, in);
    }

}
