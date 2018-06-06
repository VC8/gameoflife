package de.cassens.gameoflife.messaging.model.message;

import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.MessageType;

public class CommandMessage extends Message {

    private final CommandType commandType;

    CommandMessage(MessageType messageType, CommandType commandType) {
        super(messageType);
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
