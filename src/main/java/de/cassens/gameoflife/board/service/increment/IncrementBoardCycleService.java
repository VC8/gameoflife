package de.cassens.gameoflife.board.service.increment;

import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.event.BoardEventFactory;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.core.cellstateupdater.CellStateUpdater;
import de.cassens.gameoflife.messaging.EventEmitter;
import de.cassens.gameoflife.messaging.model.type.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IncrementBoardCycleService {

    private final BoardStateService boardStateService;
    private final BoardEventFactory boardEventFactory;
    private final BoardEventRepository boardEventRepository;
    private final EventEmitter eventEmitter;

    @Autowired
    public IncrementBoardCycleService(BoardStateService boardStateService, BoardEventFactory boardEventFactory,
                                      BoardEventRepository boardEventRepository, EventEmitter eventEmitter) {
        this.boardStateService = boardStateService;
        this.boardEventFactory = boardEventFactory;
        this.boardEventRepository = boardEventRepository;
        this.eventEmitter = eventEmitter;
    }

    public void incrementBoardCycle() throws IOException {
        // get last state
        Board state = boardStateService.getState();
        Cell[][] cells = state.getCells();

        // increment cycle
        CellStateUpdater cellStateUpdater = new CellStateUpdater(cells);
        Cell[][] updatedCells = cellStateUpdater.updateCellStates();

        // create new event
        int generation = state.getGeneration() + 1;
        BoardEvent boardIncrementedEvent = boardEventFactory.createBoardIncrementedEvent(generation, updatedCells);

        // save new event
        boardEventRepository.save(boardIncrementedEvent);

        // emit event
        eventEmitter.emitEvent(boardIncrementedEvent);
    }
}
