package com.nnon.springwithrabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterQueueConsummer {
    @RabbitListener(queues = "QD")
    public void receiveD(String msg){
        System.out.println("收到消息------"+msg);
    }
}
