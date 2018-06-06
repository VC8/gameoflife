// TODO CommandDispatcherTest

//package de.cassens.gameoflife.messaging;
//
//import de.cassens.gameoflife.board.service.increment.IncrementBoardCycleService;
//import de.cassens.gameoflife.messaging.model.type.CommandType;
//import de.cassens.gameoflife.messaging.model.message.Message;
//import de.cassens.gameoflife.messaging.model.type.MessageType;
//import org.junit.Test;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class CommandDispatcherTest {
//
//    private final IncrementBoardCycleService incrementBoardCycleService = mock(IncrementBoardCycleService.class);
//    private final CommandDispatcher commandDispatcher = new CommandDispatcher(incrementBoardCycleService);
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldDispatchIncrementBoardCommand() {
//        // given
//        Message<CommandType> message = mock(Message.class);
//        when(message.getMessageType()).thenReturn(MessageType.COMMAND);
//        when(message.getPayload()).thenReturn(CommandType.INCREMENT);
//
//        // when
//        commandDispatcher.dispatchCommandFromMessage(message);
//
//        // then
//        verify(incrementBoardCycleService).incrementBoardCycle();
//    }
//}