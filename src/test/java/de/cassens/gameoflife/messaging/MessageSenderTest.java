package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.messaging.model.EventType;
import de.cassens.gameoflife.messaging.model.Message;
import de.cassens.gameoflife.messaging.model.MessageFactory;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageSenderTest {

    private final MessageBrokerChannelService messageBrokerChannelService = mock(MessageBrokerChannelService.class);
    private final MessageFactory messageFactory = mock(MessageFactory.class);
    private final MessageConverter messageConverter = mock(MessageConverter.class);
    private final MessageSender messageSender = new MessageSender(messageBrokerChannelService, messageFactory, messageConverter);

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendCreatedEventMessage() throws IOException {
        // given
        final Channel channel = givenChannel();
        final Message message = mock(Message.class);
        when(messageFactory.createEventMessage(EventType.CREATED)).thenReturn(message);
        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"CREATED\"}";
        when(messageConverter.convertToJsonString(message)).thenReturn(eventJson);

        // when
        messageSender.sendEventMessage(EventType.CREATED);

        // then
        verify(channel).queueDeclare("BOARD_EVENTS", false, false, false, null);
        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendIncrementedEventMessage() throws IOException {
        // given
        final Channel channel = givenChannel();
        final Message message = mock(Message.class);
        when(messageFactory.createEventMessage(EventType.INCREMENTED)).thenReturn(message);
        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"INCREMENTED\"}";
        when(messageConverter.convertToJsonString(message)).thenReturn(eventJson);

        // when
        messageSender.sendEventMessage(EventType.INCREMENTED);

        // then
        verify(channel).queueDeclare("BOARD_EVENTS", false, false, false, null);
        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendDecrementedEventMessage() throws IOException {
        // given
        final Channel channel = givenChannel();
        final Message message = mock(Message.class);
        when(messageFactory.createEventMessage(EventType.DECREMENTED)).thenReturn(message);
        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"DECREMENTED\"}";
        when(messageConverter.convertToJsonString(message)).thenReturn(eventJson);

        // when
        messageSender.sendEventMessage(EventType.DECREMENTED);

        // then
        verify(channel).queueDeclare("BOARD_EVENTS", false, false, false, null);
        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendBoardStateMessage() throws IOException {
        // given
        final Channel channel = givenChannel();
        final Cell[][] cells = TestBoardFactory.createBoard();
        final Board board = new Board(cells, 2);
        final Message message = mock(Message.class);
        when(messageFactory.createDocumentMessage(board)).thenReturn(message);
        final String documentJson = getJson("document-message.json");
        when(messageConverter.convertToJsonString(message)).thenReturn(documentJson);

        // when
        messageSender.sendDocumentMessage(board);

        // then
        verify(channel).queueDeclare("BOARD_EVENTS", false, false, false, null);
        verify(channel).basicPublish("", "BOARD_EVENTS", null, documentJson.getBytes());
    }

    private Channel givenChannel() {
        final Channel channel = mock(Channel.class);
        when(messageBrokerChannelService.getChannel()).thenReturn(channel);
        return channel;
    }

    private String getJson(String file) throws IOException {
        final Stream<String> lines = Files.lines(Paths.get("src/test/resources/" + file));
        final Optional<String> optional = lines.findFirst();
        return optional.orElse("");
    }
}