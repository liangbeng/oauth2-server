package org.wzp.oauth2.mqtt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MQTT相关配置
 *
 * @Author: zp.wei
 * @DATE: 2020/11/18 10:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq.mqtt")
public class MqttConfig {


    /**
     * RabbitMQ连接用户名
     */
    private String username;

    /**
     * RabbitMQ连接密码
     */
    private String password;

    /**
     * RabbitMQ的MQTT默认topic
     */
    private String defaultTopic;

    /**
     * RabbitMQ的MQTT连接地址
     */
    private String url;


}
