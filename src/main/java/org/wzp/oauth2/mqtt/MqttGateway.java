package org.wzp.oauth2.mqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * MQTT网关，通过接口将数据传递到集成流
 *
 * @Author: zp.wei
 * @DATE: 2020/11/18 10:24
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttProductChannel")
public interface MqttGateway {

    /**
     * 发送消息到默认topic
     */
    void sendToDefaultMqtt(@RequestBody String payload);

    /**
     * 发送消息到指定topic
     */
    void sendToAssignMqtt(@Header(MqttHeaders.TOPIC) String topic, String payload);

    /**
     * 发送消息到指定topic并设置QOS
     */
    void sendToAssignMqttAndQOS(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

}
