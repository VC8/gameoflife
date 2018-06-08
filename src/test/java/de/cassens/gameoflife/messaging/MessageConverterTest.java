package de.cassens.gameoflife.messaging;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.messaging.model.message.*;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.messaging.model.type.MessageType;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MessageConverterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final MessageFactory messageFactory = new MessageFactory();
    private final MessageConverter messageConverter = new MessageConverter(messageFactory);

    @Test
    public void shouldConvertMessageObjectToJsonString() throws IOException {
        // given
        final DocumentMessage<Board> boardDocumentMessage = givenDocumentMessage();

        // when
        String jsonMessage = messageConverter.convertToJsonString(boardDocumentMessage);

        // then
        final String expectedJson = getJson("document-message.json");
        assertThat(jsonMessage, is(expectedJson));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertJsonStringToCommandMessage() throws IOException {
        // given
        String commandMessageJson = getJson("command-message.json");

        // when
        final CommandMessage commandMessage = (CommandMessage) messageConverter.convertToMessage(commandMessageJson);

        // then
        assertThat(commandMessage.getMessageType(), is(MessageType.COMMAND));
        assertThat(commandMessage.getCommandType(), is(CommandType.INCREMENT));
    }

    @Test
    public void shouldConvertJsonStringToCreateCommandMessage() throws IOException {
        // given
        String commandMessageJson = getJson("create-command-message.json");
        final BoardPayload boardPayload = new BoardPayload();
        boardPayload.setRows(3);
        boardPayload.setCols(3);

        // when
        final CommandMessage commandMessage = (CommandMessage) messageConverter.convertToMessage(commandMessageJson);

        // then
        assertThat(commandMessage.getMessageType(), is(MessageType.COMMAND));
        assertThat(commandMessage.getCommandType(), is(CommandType.CREATE));
        assertThat(commandMessage.getPayload(), is(boardPayload));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertJsonStringToEventMessage() throws IOException {
        // given
        final String eventMessageJson = getJson("event-message.json");

        // when
        final EventMessage eventMessage = (EventMessage) messageConverter.convertToMessage(eventMessageJson);

        // then
        assertThat(eventMessage.getMessageType(), is(MessageType.EVENT));
        assertThat(eventMessage.getEventType(), is(EventType.CREATED));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertJsonStringToDocumentMessage() throws IOException {
        // given
        String documentMessageJson = getJson("document-message.json");
        final Cell[][] cells = TestBoardFactory.createBoard();
        final Board expected = new Board(cells, 2);

        // when
        final DocumentMessage<Board> documentMessage = (DocumentMessage) messageConverter.convertToMessage(documentMessageJson);

        // then
        assertThat(documentMessage.getMessageType(), is(MessageType.DOCUMENT));

        final Board actual = documentMessage.getPayload();
        assertThat(actual.getGeneration(), is(expected.getGeneration()));
        assertThat(actual.getCells(), is(expected.getCells()));
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenConversionToMessageFailed() throws IOException {
        // given
        String json = getJson("wrong-message.json");

        // expected
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("mapping failed");

        // when
        messageConverter.convertToMessage(json);
    }

    private DocumentMessage<Board> givenDocumentMessage() {
        final Cell[][] cells = TestBoardFactory.createBoard();
        final Board board = new Board(cells, 2);
        final MessageFactory messageFactory = new MessageFactory();
        return messageFactory.createDocumentMessage(board);
    }

    private String getJson(String file) throws IOException {
        final Stream<String> lines = Files.lines(Paths.get("src/test/resources/" + file));
        final Optional<String> optional = lines.findFirst();
        return optional.orElse("");
    }
}