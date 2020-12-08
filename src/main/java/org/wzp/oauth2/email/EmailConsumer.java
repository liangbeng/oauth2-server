package org.wzp.oauth2.email;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.wzp.oauth2.entity.EmailMsgLog;
import org.wzp.oauth2.enumeration.EmailEnum;
import org.wzp.oauth2.mapper.EmailMsgLogMapper;
import org.wzp.oauth2.vo.EmailMsgLogVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/19 11:43
 */
@Slf4j
public class EmailConsumer {

    @Resource
    private SendEmailUtil sendEmailUtil;
    @Resource
    private EmailMsgLogMapper emailMsgLogMapper;


    //    @RabbitListener(queues = "#{emailDirectQueue.name}")
    @RabbitListener(queues = "emailDirectQueue")
    public void receiver(EmailMsgLogVO emailMsgLogVO, Message msg, Channel channel) throws IOException {
        Long id = emailMsgLogVO.getId();
        EmailMsgLog emailMsgLog = emailMsgLogMapper.selectByPrimaryKey(id);
        emailMsgLog.setStatus(1);
        try {
            log.info("-------------------正常开始-------------------");
            //判断邮件格式
            if (emailMsgLogVO.getEmailEnum().equals(EmailEnum.ORDINARY_EMAIL)) {
                sendEmailUtil.sendEmail(emailMsgLogVO);
            }
            if (emailMsgLogVO.getEmailEnum().equals(EmailEnum.ACCESSORY_EMAIL)) {
                sendEmailUtil.sendAccessoryEmail(emailMsgLogVO);
            }
            //手动ack表示确认消息。multiple：false只确认该delivery_tag的消息，true确认该delivery_tag的所有消息
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
            log.info("-------------------正常结束-------------------");
        } catch (Exception e) {
            log.info("==================异常开始1====================");
            //捕获异常后，重新发送到指定队列，自动确认不抛出异常即为ack
            Map<String, Object> headers = msg.getMessageProperties().getHeaders();
            Integer retryCount = !headers.containsKey("retry-count") ? 0 : Integer.valueOf(String.valueOf(headers.get("retry-count")));
            log.error("retryCount:" + retryCount);
            //判断是否满足最大重试次数(重试3次,rabbitmq 默认重试次数是三次)
            if (retryCount < 3) {
                log.info("==================异常开始2====================");
                headers.put("retry-count", retryCount++);
                //重新发送到MQ中
                AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().contentType("text/plain").headers(headers).build();
                channel.basicPublish(msg.getMessageProperties().getReceivedExchange(), msg.getMessageProperties().getReceivedRoutingKey(), basicProperties, msg.getBody());
            } else {
                //更改对应的投递状态
                emailMsgLog.setStatus(2);
                emailMsgLog.setTryCount(retryCount);
            }
            log.info("==================异常结束====================");
        }
        emailMsgLogMapper.updateByPrimaryKeySelective(emailMsgLog);
    }


}
