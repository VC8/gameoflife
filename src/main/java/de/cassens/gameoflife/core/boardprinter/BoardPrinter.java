package de.cassens.gameoflife.core.boardprinter;

import de.cassens.gameoflife.model.cell.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Arrays;

// TODO delete after building frontend
public class BoardPrinter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardPrinter.class);

    public void printBoard(int generation, Cell[][] board) {
        System.out.println("----- print generation: "+generation+" -----");

        Arrays.stream(board).forEach(row -> {
            Arrays.stream(row).forEach(System.out::println);
            System.out.println();
        });
    }
}
