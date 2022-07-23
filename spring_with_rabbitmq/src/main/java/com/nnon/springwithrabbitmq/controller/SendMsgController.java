package com.nnon.springwithrabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rabbit")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        System.out.println("发给两个队列消息-------"+message);
        rabbitTemplate.convertAndSend("X","XA","延迟10秒发来的消息"+message);
        rabbitTemplate.convertAndSend("X","XB","延迟40秒发来的消息"+message);
    }
    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg2(@PathVariable String message,@PathVariable String ttlTime){
        //RabbitMQ只会检查第一个消息是否过期，如果第一个消息的延时时长很长，但第二个消息的延时很短，第二个消息并不会优先得到执行
        System.out.println("发送一条时长{" + ttlTime + "}毫秒TTL信息给队列,信息为" + ttlTime);
        rabbitTemplate.convertAndSend("X","XC",message,msg->{
             msg.getMessageProperties().setExpiration(ttlTime);
             return msg;
        });
    }
}
