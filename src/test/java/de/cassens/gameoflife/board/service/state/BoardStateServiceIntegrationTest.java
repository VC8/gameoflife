package de.cassens.gameoflife.board.service.state;

import de.cassens.gameoflife.board.model.Board;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class BoardStateServiceIntegrationTest {

    @Autowired
    private BoardStateService boardStateService;

    @Test
    public void shouldQueryState() {
        // when
        Board state = boardStateService.getState();

        // then
        assertThat(state.getGeneration(), greaterThanOrEqualTo(0));
    }
}