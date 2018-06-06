package de.cassens.gameoflife.messaging.model.message;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.messaging.model.type.MessageType;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {
    public CommandMessage createCommandMessage(CommandType commandType) {
        return new CommandMessage(MessageType.COMMAND, commandType);
    }

    public EventMessage createEventMessage(EventType eventType) {
        return new EventMessage(MessageType.EVENT, eventType);
    }

    public DocumentMessage<Board> createDocumentMessage(Board board) {
        return new DocumentMessage<>(MessageType.DOCUMENT, board);
    }
}
