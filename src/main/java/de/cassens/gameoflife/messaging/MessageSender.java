package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import de.cassens.gameoflife.board.model.Board;
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

    public final void sendEventMessage(EventType eventType) throws IOException {
        final Message<EventType> eventMessage = messageFactory.createEventMessage(eventType);
        final String eventMessageJson = messageConverter.convertToJsonString(eventMessage);

        publishMessage(eventMessageJson);

        logMessage(eventMessageJson);
    }

    public final void sendDocumentMessage(Board board) throws IOException {
        final Message<Board> documentMessage = messageFactory.createDocumentMessage(board);
        final String documentMessageJson = messageConverter.convertToJsonString(documentMessage);

        publishMessage(documentMessageJson);

        logMessage(documentMessageJson);
    }

    private void publishMessage(String messageJson) throws IOException {
        final Channel channel = messageBrokerChannelService.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, messageJson.getBytes());
    }

    private void logMessage(String messageJson) {
        System.out.println("Sent '" + messageJson + "'");
    }
}
