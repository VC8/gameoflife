package de.cassens.gameoflife.messaging.model.message;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.messaging.model.type.MessageType;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

    public CommandMessage createCommandMessage(CommandType commandType) {
        if (CommandType.CREATE.equals(commandType)) throw new IllegalArgumentException("command type must not be create");
        return new CommandMessage(MessageType.COMMAND, commandType, null);
    }

    public CommandMessage createCommandMessage(CommandType commandType, BoardPayload boardPayload) {
        if (!CommandType.CREATE.equals(commandType)) throw new IllegalArgumentException("command type must be create");
        return new CommandMessage(MessageType.COMMAND, commandType, boardPayload);
    }

    public EventMessage createEventMessage(EventType eventType) {
        return new EventMessage(MessageType.EVENT, eventType);
    }

    public DocumentMessage<Board> createDocumentMessage(Board board) {
        return new DocumentMessage<>(MessageType.DOCUMENT, board);
    }
}
