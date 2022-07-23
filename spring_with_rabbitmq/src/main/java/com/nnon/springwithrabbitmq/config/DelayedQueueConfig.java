package com.nnon.springwithrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedQueueConfig {
    //交换机
    private final  String DELAYED_EXCHANGE="delayed.xchange";
    //队列
    private final String DELAYED_QUEUE = "delayed.queue";
    //routingKey
    private final String ROUTING_KEY = "delayed.routingKey";
    @Bean("delayedExchange")
    public CustomExchange delayedExchange(){
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type","direct");
        return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,arguments);
    }
    @Bean("delayedQueue")
    public Queue delayed(){
        return new Queue(DELAYED_QUEUE);
    }
    @Bean
    public Binding delayedBinding (@Qualifier("delayedExchange") CustomExchange delayedExchange, @Qualifier("delayedQueue") Queue delayedQueue){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(ROUTING_KEY).noargs();
    }
}
