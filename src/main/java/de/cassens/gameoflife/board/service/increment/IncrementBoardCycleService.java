package de.cassens.gameoflife.board.service.increment;

import de.cassens.gameoflife.board.event.BoardEvent;
import de.cassens.gameoflife.board.event.BoardEventFactory;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.repository.BoardEventRepository;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.core.cellstateupdater.CellStateUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncrementBoardCycleService {

    private BoardStateService boardStateService;
    private BoardEventFactory boardEventFactory;
    private BoardEventRepository boardEventRepository;

    @Autowired
    public IncrementBoardCycleService(BoardStateService boardStateService, BoardEventFactory boardEventFactory,
                                      BoardEventRepository boardEventRepository) {
        this.boardStateService = boardStateService;
        this.boardEventFactory = boardEventFactory;
        this.boardEventRepository = boardEventRepository;
    }

    public void incrementBoardCycle() {
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

        // TODO send event message
    }
}
