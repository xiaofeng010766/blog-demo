package com.demo.mqconsumer;

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
public class ThirdConsumer {

    @RabbitListener(queues = {"third-queue"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message) throws Exception {
        // 处理消息
        System.out.println("ThirdConsumer {} handleMessage :"+message);
    }

}
