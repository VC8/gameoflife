package de.cassens.gameoflife.cell.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Cell {

    int row;
    int col;
    boolean isAlive;

    @Override
    public String toString() {
        int alive = this.isAlive ? 1 : 0;
        return "[ "+ alive +" ]";
    }
}
