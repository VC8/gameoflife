package de.cassens.gameoflife.messaging;

import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.board.service.create.CreateBoardService;
import de.cassens.gameoflife.board.service.decrement.DecrementBoardCycleService;
import de.cassens.gameoflife.board.service.increment.IncrementBoardCycleService;
import de.cassens.gameoflife.messaging.model.message.CommandMessage;
import de.cassens.gameoflife.messaging.model.message.EventMessage;
import de.cassens.gameoflife.messaging.model.message.MessageFactory;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.messaging.model.type.MessageType;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CommandDispatcherTest {

    private final CreateBoardService createBoardService = mock(CreateBoardService.class);
    private final IncrementBoardCycleService incrementBoardCycleService = mock(IncrementBoardCycleService.class);
    private final DecrementBoardCycleService decrementBoardCycleService = mock(DecrementBoardCycleService.class);
    private final CommandDispatcher commandDispatcher = new CommandDispatcher(createBoardService,
                                                                              incrementBoardCycleService,
                                                                              decrementBoardCycleService);

    @Test
    public void shouldDispatchIncrementBoardCommand() throws IOException {
        // given
        CommandMessage message = givenCommandMessage(CommandType.INCREMENT);

        // when
        commandDispatcher.dispatchCommandFromMessage(message);

        // then
        verify(incrementBoardCycleService).incrementBoardCycle();
    }

    @Test
    public void shouldDispatchDecrementBoardCommand() throws IOException {
        // given
        CommandMessage message = givenCommandMessage(CommandType.DECREMENT);

        // when
        commandDispatcher.dispatchCommandFromMessage(message);

        // then
        verify(decrementBoardCycleService).decrementBoardCycle();
    }

    @Test
    public void shouldDispatchCreateBoardCommand() throws IOException {
        // given
        CommandMessage message = givenCommandMessage(CommandType.CREATE);
        final int count = 2;
        givenMessagePayload(message,count);

        // when
        commandDispatcher.dispatchCommandFromMessage(message);

        // then
        verify(createBoardService).createBoard(count, count);
    }

    @Test
    public void shouldDoNothingWhenMessageTypeIsNotCommand() throws IOException {
        // given
        MessageFactory messageFactory = new MessageFactory();
        final EventMessage eventMessage = messageFactory.createEventMessage(EventType.CREATED);

        // when
        commandDispatcher.dispatchCommandFromMessage(eventMessage);

        // then
        verify(createBoardService, never()).createBoard(anyInt(), anyInt());
        verify(incrementBoardCycleService, never()).incrementBoardCycle();
        verify(decrementBoardCycleService, never()).decrementBoardCycle();
    }

    private CommandMessage givenCommandMessage(CommandType commandType) {
        CommandMessage message = mock(CommandMessage.class);
        when(message.getMessageType()).thenReturn(MessageType.COMMAND);
        when(message.getCommandType()).thenReturn(commandType);
        return message;
    }

    private void givenMessagePayload(CommandMessage message, int count) {
        final BoardPayload payload = new BoardPayload();
        payload.setRows(count);
        payload.setCols(count);
        when(message.getPayload()).thenReturn(payload);
    }
}