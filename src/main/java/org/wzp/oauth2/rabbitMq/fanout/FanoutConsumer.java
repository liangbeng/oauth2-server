package org.wzp.oauth2.rabbitMq.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * 消费者
 *
 * @Author: zp.wei
 * @DATE: 2020/11/17 13:16
 */
@Slf4j
public class FanoutConsumer {


    @RabbitListener(queues = "#{fanoutQueue1.name}")
    public void receive1(String in) {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{fanoutQueue2.name}")
    public void receive2(String in) {
        receive(in, 2);
    }

    private void receive(String in, int receiver) {
        log.info("instance {} [消费者接收消息] Received '{}'", receiver, in);

    }

}
