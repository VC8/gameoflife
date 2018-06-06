// TODO CommandDispatcher
//package de.cassens.gameoflife.messaging;
//
//import de.cassens.gameoflife.board.service.increment.IncrementBoardCycleService;
//import de.cassens.gameoflife.messaging.model.type.CommandType;
//import de.cassens.gameoflife.messaging.model.message.Message;
//import de.cassens.gameoflife.messaging.model.type.MessageType;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CommandDispatcher {
//
//    private final IncrementBoardCycleService incrementBoardCycleService;
//
//    public CommandDispatcher(IncrementBoardCycleService incrementBoardCycleService) {
//        this.incrementBoardCycleService = incrementBoardCycleService;
//    }
//
//    public void dispatchCommandFromMessage(Message<CommandType> message) {
//        final MessageType messageType = message.getMessageType();
//
//        if (MessageType.COMMAND.equals(messageType)) {
//            final CommandType commandType = message.getPayload();
//
//            if (CommandType.INCREMENT.equals(commandType)) {
//                incrementBoardCycleService.incrementBoardCycle();
//            }
//        }
//    }
//}
