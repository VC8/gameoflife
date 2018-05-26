import Cell from '../../model/cell/Cell';

export default class Board {
    private cells: Cell[][];

    public constructor(cells: Cell[][] = [[]]) {
        this.cells = cells;
    }

    public getCells(): Cell[][] {
        return this.cells;
    }
}