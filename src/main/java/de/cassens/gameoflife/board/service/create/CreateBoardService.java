package de.cassens.gameoflife.board.service.create;

import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.messaging.EventEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CreateBoardService {

    private final BoardEventFactory boardEventFactory;
    private final BoardEventRepository boardEventRepository;
    private final EventEmitter eventEmitter;

    @Autowired
    public CreateBoardService(BoardEventFactory boardEventFactory,
                              BoardEventRepository boardEventRepository,
                              EventEmitter eventEmitter) {
        this.boardEventFactory = boardEventFactory;
        this.boardEventRepository = boardEventRepository;
        this.eventEmitter = eventEmitter;
    }

    public void createBoard(int rows, int cols) throws IOException {
        BoardEvent boardCreatedEvent = boardEventFactory.createBoardCreatedEvent(rows, cols);

        boardEventRepository.save(boardCreatedEvent);

        eventEmitter.emitEvent(boardCreatedEvent);
    }
}
