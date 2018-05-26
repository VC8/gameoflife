package de.cassens.gameoflife.board.service.create;

import de.cassens.gameoflife.board.event.BoardEvent;
import de.cassens.gameoflife.board.event.BoardEventFactory;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateBoardService {

    private BoardEventFactory boardEventFactory;
    private BoardEventRepository boardEventRepository;

    @Autowired
    public CreateBoardService(BoardEventFactory boardEventFactory, BoardEventRepository boardEventRepository) {
        this.boardEventFactory = boardEventFactory;
        this.boardEventRepository = boardEventRepository;
    }

    public void createBoard(int rows, int cols) {
        BoardEvent boardCreatedEvent = boardEventFactory.createBoardCreatedEvent(rows, cols);

        boardEventRepository.save(boardCreatedEvent);

        // TODO emit event / send event message
    }
}
