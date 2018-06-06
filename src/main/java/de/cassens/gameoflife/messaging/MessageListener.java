package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.*;
import de.cassens.gameoflife.messaging.config.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.text.MessageFormat.*;

@Component
public class MessageListener extends DefaultConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    public MessageListener(Channel channel) throws IOException {
        super(channel);
        channel.basicConsume(Queues.BOARD_EVENTS.getQueueName(), this);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        String message = new String(body, "UTF-8");
        final String logMessage = format("Received ''{0}''", message);
        LOGGER.info(logMessage);
    }
}
