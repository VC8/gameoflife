package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import de.cassens.gameoflife.messaging.model.EventType;
import de.cassens.gameoflife.messaging.model.Message;
import de.cassens.gameoflife.messaging.model.MessageFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageSender {

    private static final String QUEUE_NAME = "BOARD_EVENTS";
    private final MessageBrokerChannelService messageBrokerChannelService;
    private final MessageFactory messageFactory;
    private final MessageConverter messageConverter;

    public MessageSender(MessageBrokerChannelService messageBrokerChannelService,
                         MessageFactory messageFactory,
                         MessageConverter messageConverter) {
        this.messageBrokerChannelService = messageBrokerChannelService;
        this.messageFactory = messageFactory;
        this.messageConverter = messageConverter;
    }

    public void sendCreatedEventMessage() throws IOException {
        final Message<EventType> eventMessage = messageFactory.createEventMessage(EventType.CREATED);
        final String eventMessageJson = messageConverter.convertToJsonString(eventMessage);

        final Channel channel = messageBrokerChannelService.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, eventMessageJson.getBytes());

        System.out.println("Sent '" + eventMessageJson + "'");
    }

    public void sendIncrementedEventMessage() {

    }
}
