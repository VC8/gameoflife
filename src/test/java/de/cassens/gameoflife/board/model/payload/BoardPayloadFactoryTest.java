package de.cassens.gameoflife.board.model.payload;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BoardPayloadFactoryTest {

    @Test
    public void shouldCreateBoardPayload() {
        // given
        final int rows = 3;
        final int cols = 3;

        // when
        final BoardPayload boardPayload = BoardPayloadFactory.createBoardPayload(rows, cols);

        // then
        assertThat(boardPayload.getRows(), is(rows));
        assertThat(boardPayload.getCols(), is(cols));
    }
}