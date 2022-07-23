package com.nnon.springwithrabbitmq.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延迟队列
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机名称
    public static final String X_EXCHANGE = "X";
    //死信交换机名称
    public static final String Y_DEAD_LETTER_EXCHANGE="Y";
    //普通队列名称
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    //死信队列名称
    public static final String DEAD_LETTER_QUEUE= "QD";


    //声明xExchange
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    //声明yExchange
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }
    //声明队列
    //声明queue_a
    @Bean("queueA")
    public Queue queueA(){
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //routingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //TTL 过期时间
        arguments.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }
    //声明queue_b
    @Bean("queueB")
    public Queue queueB(){
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //routingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //TTL 过期时间
        arguments.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    //声明queue_C
    @Bean("queueC")
    public Queue queueC(){
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //routingKey
        arguments.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();
    }
    //声明queue_b
    @Bean("queueD")
    public Queue queueD(){

        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }


    @Bean
    public Binding quequeABindingX(@Qualifier("queueA") Queue queueA,@Qualifier("xExchange")DirectExchange xExchange ){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    @Bean
    public Binding quequeBBindingX(@Qualifier("queueB") Queue queueB,@Qualifier("xExchange")DirectExchange xExchange ){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }
    @Bean
    public Binding quequeDBindingY(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange")DirectExchange yExchange ){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

    //由生产者指定过期时间
    @Bean
    public Binding quequeCBindingY(@Qualifier("queueC") Queue queueC,@Qualifier("xExchange")DirectExchange XExchange ){
        return BindingBuilder.bind(queueC).to(XExchange).with("XC");
    }

}
