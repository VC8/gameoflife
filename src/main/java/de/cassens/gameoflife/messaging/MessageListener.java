package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import de.cassens.gameoflife.messaging.config.Queues;
import de.cassens.gameoflife.messaging.model.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.text.MessageFormat.format;

@Component
public class MessageListener extends DefaultConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);
    private final MessageConverter messageConverter;
    private final CommandDispatcher commandDispatcher;

    public MessageListener(Channel channel,
                           MessageConverter messageConverter,
                           CommandDispatcher commandDispatcher) throws IOException {
        super(channel);
        this.messageConverter = messageConverter;
        this.commandDispatcher = commandDispatcher;
        channel.basicConsume(Queues.BOARD_EVENTS.getQueueName(), this);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        final String messageJson = new String(body, "UTF-8");

        logMessage(messageJson);

        final Message message = messageConverter.convertToMessage(messageJson);
        commandDispatcher.dispatchCommandFromMessage(message);
    }

    private void logMessage(String messageJson) {
        final String logMessage = format("Received ''{0}''", messageJson);
        LOGGER.info(logMessage);
    }
}
