package de.cassens.gameoflife.messaging.model.message;

import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.messaging.model.type.MessageType;

public class EventMessage extends Message {

    private final EventType eventType;

    EventMessage(MessageType messageType, EventType eventType) {
        super(messageType);
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }
}
