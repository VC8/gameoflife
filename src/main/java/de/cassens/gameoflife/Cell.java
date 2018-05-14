package de.cassens.gameoflife;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cell {

    private final int row;
    private final int col;
    private boolean isAlive;

    @Override
    public String toString() {
        int alive = this.isAlive ? 1 : 0;
        return "[ "+ alive +" ]";
    }
}
