package de.cassens.gameoflife.messaging;

import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.board.service.create.CreateBoardService;
import de.cassens.gameoflife.board.service.decrement.DecrementBoardCycleService;
import de.cassens.gameoflife.board.service.increment.IncrementBoardCycleService;
import de.cassens.gameoflife.messaging.model.message.CommandMessage;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.message.Message;
import de.cassens.gameoflife.messaging.model.type.MessageType;
import org.springframework.stereotype.Component;

@Component
public class CommandDispatcher {

    private final CreateBoardService createBoardService;
    private final IncrementBoardCycleService incrementBoardCycleService;
    private final DecrementBoardCycleService decrementBoardCycleService;

    public CommandDispatcher(CreateBoardService createBoardService,
                             IncrementBoardCycleService incrementBoardCycleService,
                             DecrementBoardCycleService decrementBoardCycleService) {
        this.createBoardService = createBoardService;
        this.incrementBoardCycleService = incrementBoardCycleService;
        this.decrementBoardCycleService = decrementBoardCycleService;
    }

    public void dispatchCommandFromMessage(Message message) {
        final MessageType messageType = message.getMessageType();

        if (MessageType.COMMAND.equals(messageType)) {
            final CommandMessage commandMessage = (CommandMessage) message;
            final CommandType commandType = commandMessage.getCommandType();

            if (CommandType.INCREMENT.equals(commandType)) {
                incrementBoardCycleService.incrementBoardCycle();
            }
            if (CommandType.DECREMENT.equals(commandType)) {
                decrementBoardCycleService.decrementBoardCycle();
            }
            if (CommandType.CREATE.equals(commandType)) {
                final BoardPayload boardPayload = commandMessage.getPayload();
                createBoardService.createBoard(boardPayload.getRows(), boardPayload.getCols());
            }
        }
    }
}
