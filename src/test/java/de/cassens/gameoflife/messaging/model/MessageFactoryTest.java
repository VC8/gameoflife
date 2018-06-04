package de.cassens.gameoflife.messaging.model;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.testUtil.TestBoardFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class MessageFactoryTest {

    private final MessageFactory messageFactory = new MessageFactory();

    @Test
    public void shouldCreateCommandMessage() {
        // given
        final CommandType commandType = CommandType.CREATE;

        // when
        Message<CommandType> message = messageFactory.createCommandMessage(commandType);

        // then
        assertThat(message.getMessageType(), is(MessageType.COMMAND));
        assertThat(message.getPayload(), is(CommandType.CREATE));
    }

    @Test
    public void shouldCreateEventMessage() {
        // given
        final EventType eventType = EventType.INCREMENTED;

        // when
        Message<EventType> message = messageFactory.createEventMessage(eventType);

        // then
        assertThat(message.getMessageType(), is(MessageType.EVENT));
        assertThat(message.getPayload(), is(EventType.INCREMENTED));
    }

    @Test
    public void shouldCreateDocumentMessage() {
        // given
        final Cell[][] cells = TestBoardFactory.createBoard();
        final Board board = new Board(cells, 2);

        // when
        Message<Board> message = messageFactory.createDocumentMessage(board);

        // then
        assertThat(message.getMessageType(), is(MessageType.DOCUMENT));
        assertThat(message.getPayload(), is(board));
    }
}