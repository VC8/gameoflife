// TODO MessageSenderTest

//package de.cassens.gameoflife.messaging;
//
//import com.rabbitmq.client.Channel;
//import de.cassens.gameoflife.board.model.Board;
//import de.cassens.gameoflife.cell.model.Cell;
//import de.cassens.gameoflife.messaging.model.type.EventType;
//import de.cassens.gameoflife.messaging.model.message.Message;
//import de.cassens.gameoflife.messaging.model.message.MessageFactory;
//import de.cassens.gameoflife.testUtil.TestBoardFactory;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.hamcrest.core.IsNull.notNullValue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.ArgumentMatchers.isNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class MessageSenderTest {
//
//    private final Channel channel = mock(Channel.class);
//    private final MessageFactory messageFactory = mock(MessageFactory.class);
//    private final MessageConverter messageConverter = mock(MessageConverter.class);
//    private final MessageSender messageSender = new MessageSender(channel, messageFactory, messageConverter);
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldSendCreatedEventMessage() throws IOException {
//        // given
//        final Message message = mock(Message.class);
//        when(messageFactory.createEventMessage(EventType.CREATED)).thenReturn(message);
//        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"CREATED\"}";
//        when(messageConverter.convertToJsonString(message)).thenReturn(eventJson);
//
//        // when
//        messageSender.sendEventMessage(EventType.CREATED);
//
//        // then
//        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldSendIncrementedEventMessage() throws IOException {
//        // given
//        final Message message = mock(Message.class);
//        when(messageFactory.createEventMessage(EventType.INCREMENTED)).thenReturn(message);
//        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"INCREMENTED\"}";
//        when(messageConverter.convertToJsonString(message)).thenReturn(eventJson);
//
//        // when
//        messageSender.sendEventMessage(EventType.INCREMENTED);
//
//        // then
//        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldSendDecrementedEventMessage() throws IOException {
//        // given
//        final Message message = mock(Message.class);
//        when(messageFactory.createEventMessage(EventType.DECREMENTED)).thenReturn(message);
//        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"DECREMENTED\"}";
//        when(messageConverter.convertToJsonString(message)).thenReturn(eventJson);
//
//        // when
//        messageSender.sendEventMessage(EventType.DECREMENTED);
//
//        // then
//        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldSendBoardStateMessage() throws IOException {
//        // given
//        final Cell[][] cells = TestBoardFactory.createBoard();
//        final Board board = new Board(cells, 2);
//        final Message message = mock(Message.class);
//        when(messageFactory.createDocumentMessage(board)).thenReturn(message);
//        final String documentJson = getJson("document-message.json");
//        when(messageConverter.convertToJsonString(message)).thenReturn(documentJson);
//
//        // when
//        messageSender.sendDocumentMessage(board);
//
//        // then
//        verify(channel).basicPublish("", "BOARD_EVENTS", null, documentJson.getBytes());
//    }
//
//    private String getJson(String file) throws IOException {
//        final Stream<String> lines = Files.lines(Paths.get("src/test/resources/" + file));
//        final Optional<String> optional = lines.findFirst();
//        return optional.orElse("");
//    }
//}