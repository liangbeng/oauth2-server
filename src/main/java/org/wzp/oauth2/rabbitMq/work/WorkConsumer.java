package org.wzp.oauth2.rabbitMq.work;

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
@RabbitListener(queues = "work")
public class WorkConsumer {


    /**
     * 工作模式
     */
    private final int instance;

    public WorkConsumer(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(String in) {
        log.info("instance {} [消费者1收到消息] Received '{}'", this.instance, in);
    }


}
