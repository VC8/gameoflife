package de.cassens.gameoflife.core.boardprinter;

import de.cassens.gameoflife.model.cell.Cell;

import java.util.Arrays;

public class BoardPrinter {

    public void printBoard(int generation, Cell[][] board) {
        System.out.println("----- print generation: " + generation + " -----");
        Arrays.stream(board).forEach(row -> {
            Arrays.stream(row).forEach(System.out::print);
            System.out.println();
        });
    }
}
