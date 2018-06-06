// TODO MessageConverterTest

//package de.cassens.gameoflife.messaging;
//
//import de.cassens.gameoflife.board.model.Board;
//import de.cassens.gameoflife.cell.model.Cell;
//import de.cassens.gameoflife.messaging.model.message.Message;
//import de.cassens.gameoflife.messaging.model.message.MessageFactory;
//import de.cassens.gameoflife.messaging.model.type.CommandType;
//import de.cassens.gameoflife.messaging.model.type.EventType;
//import de.cassens.gameoflife.messaging.model.type.MessageType;
//import de.cassens.gameoflife.testUtil.TestBoardFactory;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//
//public class MessageConverterTest {
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    private final MessageFactory messageFactory = new MessageFactory();
//    private final MessageConverter messageConverter = new MessageConverter(messageFactory);
//
//    @Test
//    public void shouldConvertMessageObjectToJsonString() throws IOException {
//        // given
//        final Message<Board> documentMessage = givenDocumentMessage();
//
//        // when
//        String jsonMessage = messageConverter.convertToJsonString(documentMessage);
//
//        // then
//        final String expectedJson = getJson("document-message.json");
//        assertThat(jsonMessage, is(expectedJson));
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldConvertJsonStringToCommandMessage() throws IOException {
//        // given
//        String commandMessageJson = getJson("command-message.json");
//
//        // when
//        Message<CommandType> message = messageConverter.convertToMessage(commandMessageJson);
//
//        // then
//        assertThat(message.getMessageType(), is(MessageType.COMMAND));
//        assertThat(message.getPayload(), is(CommandType.CREATE));
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldConvertJsonStringToEventMessage() throws IOException {
//        // given
//        String commandMessageJson = getJson("event-message.json");
//
//        // when
//        Message<EventType> message = messageConverter.convertToMessage(commandMessageJson);
//
//        // then
//        assertThat(message.getMessageType(), is(MessageType.EVENT));
//        assertThat(message.getPayload(), is(EventType.CREATED));
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldConvertJsonStringToDocumentMessage() throws IOException {
//        // given
//        String documentMessageJson = getJson("document-message.json");
//        final Cell[][] cells = TestBoardFactory.createBoard();
//        final Board expected = new Board(cells, 2);
//
//        // when
//        Message<Board> message = messageConverter.convertToMessage(documentMessageJson);
//
//        // then
//        assertThat(message.getMessageType(), is(MessageType.DOCUMENT));
//
//        final Board actual = message.getPayload();
//        assertThat(actual.getGeneration(), is(expected.getGeneration()));
//        assertThat(actual.getCells(), is(expected.getCells()));
//    }
//
//    @Test
//    public void shouldThrowIllegalStateExceptionWhenConversionToMessageFailed() throws IOException {
//        // given
//        String json = getJson("wrong-message.json");
//
//        // expected
//        expectedException.expect(IllegalStateException.class);
//        expectedException.expectMessage("mapping failed");
//
//        // when
//        messageConverter.convertToMessage(json);
//    }
//
//    private Message<Board> givenDocumentMessage() {
//        final Cell[][] cells = TestBoardFactory.createBoard();
//        final Board board = new Board(cells, 2);
//        final MessageFactory messageFactory = new MessageFactory();
//        return messageFactory.createDocumentMessage(board);
//    }
//
//    private String getJson(String file) throws IOException {
//        final Stream<String> lines = Files.lines(Paths.get("src/test/resources/" + file));
//        final Optional<String> optional = lines.findFirst();
//        return optional.orElse("");
//    }
//}