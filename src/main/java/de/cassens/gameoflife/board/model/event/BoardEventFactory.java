package de.cassens.gameoflife.board.model.event;

import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.cell.model.CellFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class BoardEventFactory {

    public BoardEvent createBoardCreatedEvent(int rows, int cols) {
        Cell[][] cells = createCells(rows, cols);

        return new BoardEvent(UUID.randomUUID(), new Date(), BoardEventType.CREATED, 0, cells);
    }

    public BoardEvent createBoardIncrementedEvent(int generation, Cell[][] cells) {
        return new BoardEvent(UUID.randomUUID(), new Date(), BoardEventType.INCREMENTED, generation, cells);
    }

    public BoardEvent createBoardDecrementedEvent(BoardEvent boardEvent) {
        return new BoardEvent(UUID.randomUUID(), new Date(), BoardEventType.DECREMENTED, boardEvent.getGeneration(), boardEvent.getCells());
    }

    private Cell[][] createCells(int rows, int cols) {
        Cell[][] cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isAlive = Math.random() > 0.6;
                cells[row][col] = CellFactory.createCell(row, col, isAlive);
            }
        }

        return cells;
    }
}
