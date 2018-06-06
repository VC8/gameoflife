package de.cassens.gameoflife.messaging.model.message;

import de.cassens.gameoflife.messaging.model.type.MessageType;

public class DocumentMessage<T> extends Message {

    private final T payload;

    DocumentMessage(MessageType messageType, T payload) {
        super(messageType);
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}
