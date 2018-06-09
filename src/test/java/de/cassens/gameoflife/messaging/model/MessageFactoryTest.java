package de.cassens.gameoflife.messaging.model;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.messaging.model.message.CommandMessage;
import de.cassens.gameoflife.messaging.model.message.DocumentMessage;
import de.cassens.gameoflife.messaging.model.message.EventMessage;
import de.cassens.gameoflife.messaging.model.message.MessageFactory;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.messaging.model.type.MessageType;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MessageFactoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final MessageFactory messageFactory = new MessageFactory();

    @Test
    public void shouldThrowExceptionWhenCommandTypeIsCreateWithoutPayload() {
        // given
        final CommandType commandType = CommandType.CREATE;

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("command type must not be create");

        // when
        messageFactory.createCommandMessage(commandType);
    }

    @Test
    public void shouldCreateIncrementCommandMessage() {
        // given
        final CommandType commandType = CommandType.INCREMENT;

        // when
        final CommandMessage commandMessage = messageFactory.createCommandMessage(commandType);

        // then
        assertThat(commandMessage.getMessageType(), is(MessageType.COMMAND));
        assertThat(commandMessage.getCommandType(), is(CommandType.INCREMENT));
    }

    @Test
    public void shouldThrowExceptionWhenCommandTypeIsNotCreateWithPayload() {
        // given
        final CommandType commandType = CommandType.DECREMENT;

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("command type must be create");

        // when
        messageFactory.createCommandMessage(commandType, new BoardPayload());
    }

    @Test
    public void shouldCreateCreateCommandMessage() {
        // given
        final CommandType commandType = CommandType.CREATE;
        final BoardPayload payload = new BoardPayload();

        // when
        final CommandMessage commandMessage = messageFactory.createCommandMessage(commandType, payload);

        // then
        assertThat(commandMessage.getMessageType(), is(MessageType.COMMAND));
        assertThat(commandMessage.getCommandType(), is(CommandType.CREATE));
        assertThat(commandMessage.getPayload(), is(payload));
    }

    @Test
    public void shouldCreateEventMessage() {
        // given
        final EventType eventType = EventType.INCREMENTED;

        // when
        final EventMessage eventMessage = messageFactory.createEventMessage(eventType);

        // then
        assertThat(eventMessage.getMessageType(), is(MessageType.EVENT));
        assertThat(eventMessage.getEventType(), is(EventType.INCREMENTED));
    }

    @Test
    public void shouldCreateDocumentMessage() {
        // given
        final Cell[][] cells = TestBoardFactory.createBoard();
        final Board board = new Board(cells, 2);

        // when
        final DocumentMessage<Board> documentMessage = messageFactory.createDocumentMessage(board);

        // then
        assertThat(documentMessage.getMessageType(), is(MessageType.DOCUMENT));
        assertThat(documentMessage.getPayload(), is(board));
    }
}