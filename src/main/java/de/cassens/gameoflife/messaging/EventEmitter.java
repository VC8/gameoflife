package de.cassens.gameoflife.messaging;

import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EventEmitter {

    private final MessageSender messageSender;

    public EventEmitter(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void emitEvent(BoardEvent boardEvent) throws IOException {
        final BoardEventType boardEventType = boardEvent.getBoardEventType();
        final EventType eventType = EventType.valueOf(boardEventType.name());
        messageSender.sendEventMessage(eventType);
    }
}
