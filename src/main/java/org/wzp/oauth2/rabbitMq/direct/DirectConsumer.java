package org.wzp.oauth2.rabbitMq.direct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/17 14:27
 */
@Slf4j
public class DirectConsumer {


    @RabbitListener(queues = "#{directQueue1.name}")
    public void receive1(String in) {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{directQueue2.name}")
    public void receive2(String in) {
        receive(in, 2);
    }


    private void receive(String in, int receiver) {
        log.info("instance {} [消费者接收消息] Received '{}'", receiver, in);
    }

}
