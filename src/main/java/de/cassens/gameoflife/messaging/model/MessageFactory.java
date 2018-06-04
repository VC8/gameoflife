package de.cassens.gameoflife.messaging.model;

import de.cassens.gameoflife.board.model.Board;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {
    public Message<ActionType> createCommandMessage(ActionType actionType) {
        return new Message<>(MessageType.COMMAND, actionType);
    }

    public Message<EventType> createEventMessage(EventType eventType) {
        return new Message<>(MessageType.EVENT, eventType);
    }

    public Message<Board> createDocumentMessage(Board board) {
        return new Message<>(MessageType.DOCUMENT, board);
    }
}
