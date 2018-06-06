package de.cassens.gameoflife.messaging.model;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.messaging.model.message.*;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.messaging.model.type.MessageType;
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
        final CommandMessage commandMessage = messageFactory.createCommandMessage(commandType);

        // then
        assertThat(commandMessage.getMessageType(), is(MessageType.COMMAND));
        assertThat(commandMessage.getCommandType(), is(CommandType.CREATE));
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