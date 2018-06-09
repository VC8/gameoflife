package de.cassens.gameoflife.messaging;

import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class EventEmitterTest {

    private final MessageSender messageSender = mock(MessageSender.class);
    private final EventEmitter eventEmitter = new EventEmitter(messageSender);
    private BoardEvent boardEvent = mock(BoardEvent.class);

    @Test
    public void shouldEmitCreatedEvent() throws IOException {
        // given
        when(boardEvent.getBoardEventType()).thenReturn(BoardEventType.CREATED);

        // when
        eventEmitter.emitEvent(boardEvent);

        // then
        verify(messageSender).sendEventMessage(EventType.CREATED);
    }

    @Test
    public void shouldEmitIncrementedEvent() throws IOException {
        // given
        when(boardEvent.getBoardEventType()).thenReturn(BoardEventType.INCREMENTED);

        // when
        eventEmitter.emitEvent(boardEvent);

        // then
        verify(messageSender).sendEventMessage(EventType.INCREMENTED);
    }

    @Test
    public void shouldEmitDecrementedEventMessage() throws IOException {
        // given
        when(boardEvent.getBoardEventType()).thenReturn(BoardEventType.DECREMENTED);

        // when
        eventEmitter.emitEvent(boardEvent);

        // then
        verify(messageSender).sendEventMessage(EventType.DECREMENTED);
    }
}