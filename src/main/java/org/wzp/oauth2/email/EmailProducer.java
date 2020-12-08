package org.wzp.oauth2.email;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.wzp.oauth2.config.CustomConfig;
import org.wzp.oauth2.vo.EmailMsgLogVO;

import javax.annotation.Resource;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/20 10:44
 */
public class EmailProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final String exchangeName = CustomConfig.emailExchange;
    private static final String routingKey = CustomConfig.routingKey;

    //添加到rabbit队列中
    public void sendEmail(EmailMsgLogVO emailMsgLogVO) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, emailMsgLogVO);
    }

}
