package org.wzp.oauth2.controller.back;

import cn.hutool.core.thread.ThreadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzp.oauth2.config.BaseConfig;
import org.wzp.oauth2.util.Result;
import org.wzp.oauth2.rabbitMq.direct.DirectProducer;
import org.wzp.oauth2.rabbitMq.fanout.FanoutProducer;
import org.wzp.oauth2.rabbitMq.simple.SimpleProducer;
import org.wzp.oauth2.rabbitMq.topic.TopicProducer;
import org.wzp.oauth2.rabbitMq.work.WorkProducer;

import javax.annotation.Resource;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/17 13:16
 */
@Api(tags = "rabbitMq管理")
@Slf4j
@RestController
@RequestMapping("/back/rabbitMq")
public class RabbitController extends BaseConfig {

    @Resource
    private SimpleProducer simpleProducer;
    @Resource
    private WorkProducer workProducer;
    @Resource
    private FanoutProducer fanoutProducer;
    @Resource
    private DirectProducer directProducer;
    @Resource
    private TopicProducer topicProducer;


    @ApiOperation("简单模式")
    @GetMapping("/simple")
    public Result simpleTest() {
        for (int i = 0; i < 5; i++) {
            simpleProducer.send();
            ThreadUtil.sleep(1000);
        }
        return Result.ok();
    }


    @ApiOperation("工作模式")
    @GetMapping("/work")
    public Result workTest() {
        for (int i = 0; i < 5; i++) {
            workProducer.send(i);
            ThreadUtil.sleep(1000);
        }
        return Result.ok();
    }


    @ApiOperation("发布/订阅模式")
    @GetMapping("/fanout")
    public Result fanoutTest() {
        for (int i = 0; i < 5; i++) {
            fanoutProducer.send(i);
            ThreadUtil.sleep(1000);
        }
        return Result.ok();
    }


    @ApiOperation("路由模式")
    @GetMapping("direct")
    public Result directTest() {
        for (int i = 0; i < 5; i++) {
            directProducer.send(i);
            ThreadUtil.sleep(1000);
        }
        return Result.ok();
    }


    @ApiOperation("通配符模式")
    @GetMapping("/topic")
    public Result topicTest() {
        for (int i = 0; i < 5; i++) {
            topicProducer.send(i);
            ThreadUtil.sleep(1000);
        }
        return Result.ok();
    }


}
