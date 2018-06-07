package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.messaging.config.Queues;
import de.cassens.gameoflife.messaging.model.message.DocumentMessage;
import de.cassens.gameoflife.messaging.model.message.EventMessage;
import de.cassens.gameoflife.messaging.model.message.MessageFactory;
import de.cassens.gameoflife.messaging.model.type.EventType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageSender {

    private final Channel channel;
    private final MessageFactory messageFactory;
    private final MessageConverter messageConverter;

    public MessageSender(Channel channel,
                         MessageFactory messageFactory,
                         MessageConverter messageConverter) {
        this.channel = channel;
        this.messageFactory = messageFactory;
        this.messageConverter = messageConverter;
    }

    public void sendEventMessage(EventType eventType) throws IOException {
        final EventMessage eventMessage = messageFactory.createEventMessage(eventType);
        final String eventMessageJson = messageConverter.convertToJsonString(eventMessage);

        publishMessage(eventMessageJson);

        logMessage(eventMessageJson);
    }

    public void sendDocumentMessage(Board board) throws IOException {
        final DocumentMessage<Board> documentMessage = messageFactory.createDocumentMessage(board);
        final String documentMessageJson = messageConverter.convertToJsonString(documentMessage);

        publishMessage(documentMessageJson);

        logMessage(documentMessageJson);
    }

    private void publishMessage(String messageJson) throws IOException {
        this.channel.basicPublish("", Queues.BOARD_EVENTS.getQueueName(), null, messageJson.getBytes());
    }

    private void logMessage(String messageJson) {
        System.out.println("Sent '" + messageJson + "'");
    }
}
