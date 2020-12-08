package org.wzp.oauth2.rabbitMq.work;

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
public class WorkProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final String queueName = "work";


    /**
     * 工作模式
     *
     * @param index
     */
    public void send(int index) {
        StringBuilder builder = new StringBuilder("Hello");
        int limitIndex = index % 3 + 1;
        for (int i = 0; i < limitIndex; i++) {
            builder.append('.');
        }
        builder.append(index + 1);
        String message = builder.toString();
        rabbitTemplate.convertAndSend(queueName, message);
        log.info(" [工作模式发送消息] Sent '{}'", message);
    }

}
