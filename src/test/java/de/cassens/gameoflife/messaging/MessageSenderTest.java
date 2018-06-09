package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.messaging.model.message.DocumentMessage;
import de.cassens.gameoflife.messaging.model.message.EventMessage;
import de.cassens.gameoflife.messaging.model.message.MessageFactory;
import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Test;

import java.io.IOException;

import static de.cassens.gameoflife.testUtil.Json.getJson;
import static org.mockito.Mockito.*;

public class MessageSenderTest {

    private final Channel channel = mock(Channel.class);
    private final MessageFactory messageFactory = mock(MessageFactory.class);
    private final MessageConverter messageConverter = mock(MessageConverter.class);
    private final MessageSender messageSender = new MessageSender(channel, messageFactory, messageConverter);

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendCreatedEventMessage() throws IOException {
        // given
        final EventMessage eventMessage = mock(EventMessage.class);
        when(messageFactory.createEventMessage(EventType.CREATED)).thenReturn(eventMessage);
        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"CREATED\"}";
        when(messageConverter.convertToJsonString(eventMessage)).thenReturn(eventJson);

        // when
        messageSender.sendEventMessage(EventType.CREATED);

        // then
        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendIncrementedEventMessage() throws IOException {
        // given
        final EventMessage eventMessage = mock(EventMessage.class);
        when(messageFactory.createEventMessage(EventType.INCREMENTED)).thenReturn(eventMessage);
        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"INCREMENTED\"}";
        when(messageConverter.convertToJsonString(eventMessage)).thenReturn(eventJson);

        // when
        messageSender.sendEventMessage(EventType.INCREMENTED);

        // then
        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendDecrementedEventMessage() throws IOException {
        // given
        final EventMessage eventMessage = mock(EventMessage.class);
        when(messageFactory.createEventMessage(EventType.DECREMENTED)).thenReturn(eventMessage);
        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"DECREMENTED\"}";
        when(messageConverter.convertToJsonString(eventMessage)).thenReturn(eventJson);

        // when
        messageSender.sendEventMessage(EventType.DECREMENTED);

        // then
        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendBoardStateMessage() throws IOException {
        // given
        final Cell[][] cells = TestBoardFactory.createBoard();
        final Board board = new Board(cells, 2);
        final DocumentMessage documentMessage = mock(DocumentMessage.class);
        when(messageFactory.createDocumentMessage(board)).thenReturn(documentMessage);
        final String documentJson = getJson("document-message.json");
        when(messageConverter.convertToJsonString(documentMessage)).thenReturn(documentJson);

        // when
        messageSender.sendDocumentMessage(board);

        // then
        verify(channel).basicPublish("", "BOARD_EVENTS", null, documentJson.getBytes());
    }
}