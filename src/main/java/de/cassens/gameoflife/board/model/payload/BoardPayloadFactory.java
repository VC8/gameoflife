package de.cassens.gameoflife.board.model.payload;

public class BoardPayloadFactory {

    private BoardPayloadFactory() {
    }

    public static BoardPayload createBoardPayload(int rows, int cols) {
        final BoardPayload boardPayload = new BoardPayload();
        boardPayload.setRows(rows);
        boardPayload.setCols(cols);
        return boardPayload;
    }
}
