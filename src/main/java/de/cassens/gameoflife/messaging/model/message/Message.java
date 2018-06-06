package de.cassens.gameoflife.messaging.model.message;

import de.cassens.gameoflife.messaging.model.type.MessageType;

public abstract class Message {
    private final MessageType messageType;

    Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
