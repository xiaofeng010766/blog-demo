package com.demo.mqconsumer;

import com.demo.common.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: zhukai
 * Date: 2019/1/6
 * Time: 14:11
 * Description:
 */
@Component
public class DelayConsumer implements ChannelAwareMessageListener {
    public static CountDownLatch latch;
    public static final String FAIL_MESSAGE = "This message will fail";
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            processMessage(message);
        }
        catch (Exception e) {
            // 如果发生了异常，则将该消息重定向到缓冲队列，会在一定延迟之后自动重做
            channel.basicPublish(RabbitMqConfig.QUEUE_TTL_EXCHANGE_NAME, RabbitMqConfig.QUEUE_TTL_ROUTING_KEY, null,
                    "The failed message will auto retry after a certain delay".getBytes());
        }

        if (latch != null) {
            latch.countDown();
        }
    }

    /**
     * 模拟消息处理。如果当消息内容为FAIL_MESSAGE的话，则需要抛出异常
     *
     * @param message
     * @throws Exception
     */
    public void processMessage(Message message) throws Exception {
        String realMessage = new String(message.getBody());
        System.out.println("Received <" + realMessage + ">");
        if (Objects.equals(realMessage, FAIL_MESSAGE)) {
            throw new Exception("Some exception happened");
        }
    }

}
