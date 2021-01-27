package org.wzp.oauth2.controller.back;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.mqtt.MqttGateway;

import java.util.Map;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/18 10:26
 */
@Api(tags = "Mqtt管理")
@Slf4j
@RestController
@RequestMapping("/back/Mqtt")
public class MqttController extends BaseConfig {

    @Autowired
    private MqttGateway mqttGateway;


    @ApiOperation("向默认主题发送消息")
    @PostMapping("/defaultTopic")
    public Result sendToDefaultTopic(@RequestBody Map<String, String> map) {
        mqttGateway.sendToDefaultMqtt(map.get("payload"));
        return Result.ok();
    }


    @ApiOperation("向指定主题发送消息")
    @PostMapping("/assignMqtt")
    public Result AssignMqtt(@RequestBody Map<String, String> map) {
        mqttGateway.sendToAssignMqtt(map.get("topic"), map.get("payload"));
        log.info(map.get("topic"));
        log.info(map.get("payload"));
        return Result.ok();
    }


    @ApiOperation("向指定主题发送消息,并设置服务质量参数Qos")
    @PostMapping("/assignMqttAndQOS")
    public Result AssignMqttAndQOS(@RequestBody Map<String, String> map) {
        mqttGateway.sendToAssignMqttAndQOS(map.get("topic"), 1, map.get("payload"));
        log.info(map.get("topic"));
        log.info(map.get("payload"));
        return Result.ok();
    }

}
