package de.cassens.gameoflife.messaging.model.message;

import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.MessageType;

public class CommandMessage extends Message {

    private final CommandType commandType;
    private final BoardPayload boardPayload;

    CommandMessage(MessageType messageType, CommandType commandType, BoardPayload boardPayload) {
        super(messageType);
        this.commandType = commandType;
        this.boardPayload = boardPayload;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public BoardPayload getPayload() {
        return boardPayload;
    }
}
