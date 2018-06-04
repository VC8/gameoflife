package de.cassens.gameoflife.messaging;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.messaging.model.*;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MessageConverterTest {

    private final MessageConverter messageConverter = new MessageConverter();

    @Test
    public void shouldConvertMessageObjectToJsonString() throws IOException {
        // given
        final Message<Board> documentMessage = givenDocumentMessage();

        // when
        String jsonMessage = messageConverter.convertToJsonString(documentMessage);

        // then
        final String expectedJson = getJson("message.json");
        assertThat(jsonMessage, is(expectedJson));
    }

    // TODO exception testing message to json

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertJsonStringToCommandMessage() throws IOException {
        // given
        String commandMessageJson = getJson("command-message.json");

        // when
        Message<CommandType> message = messageConverter.convertToMessage(commandMessageJson);

        // then
        assertThat(message.getMessageType(), is(MessageType.COMMAND));
        assertThat(message.getPayload(), is(CommandType.CREATE));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertJsonStringToEventMessage() throws IOException {
        // given
        String commandMessageJson = getJson("event-message.json");

        // when
        Message<EventType> message = messageConverter.convertToMessage(commandMessageJson);

        // then
        assertThat(message.getMessageType(), is(MessageType.EVENT));
        assertThat(message.getPayload(), is(EventType.CREATED));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertJsonStringToDocumentMessage() throws IOException {
        // given
        String documentMessageJson = getJson("message.json");
        final Cell[][] cells = TestBoardFactory.createBoard();
        final Board expected = new Board(cells, 2);

        // when
        Message<Board> message = messageConverter.convertToMessage(documentMessageJson);

        // then
        assertThat(message.getMessageType(), is(MessageType.DOCUMENT));

        final Board actual = message.getPayload();
        assertThat(actual.getGeneration(), is(expected.getGeneration()));
        assertThat(actual.getCells(), is(expected.getCells()));
    }

    // TODO exception testing json to message

    private Message<Board> givenDocumentMessage() {
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