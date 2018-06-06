package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.*;
import de.cassens.gameoflife.messaging.config.Queues;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageListener {

    private final Consumer consumer;

    public MessageListener(MessageBrokerChannelService messageBrokerChannelService) throws IOException {
        final Channel channel = messageBrokerChannelService.getChannel();
        this.consumer = new Consumer(channel);
        channel.basicConsume(Queues.BOARD_EVENTS.getQueueName(), this.consumer);
    }

    private final class Consumer extends DefaultConsumer {

        public Consumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                throws IOException {
            String message = new String(body, "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
