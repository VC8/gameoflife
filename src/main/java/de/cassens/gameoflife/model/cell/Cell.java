package de.cassens.gameoflife.model.cell;

import lombok.Value;

@Value
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
