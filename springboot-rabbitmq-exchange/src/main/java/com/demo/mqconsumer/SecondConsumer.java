package com.demo.mqconsumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: zhukai
 * Date: 2019/1/4
 * Time: 10:15
 * Description:
 */
@Component
public class SecondConsumer {

    @RabbitListener(queues = {"second-queue"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message, Channel channel, Message messages) throws Exception {
        // 处理消息
        System.out.println("Second Consumer {} handleMessage :"+message);
    }

}
