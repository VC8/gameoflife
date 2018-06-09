package de.cassens.gameoflife.board.controller.state;

import de.cassens.gameoflife.board.controller.ApiMapping;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiMapping
public class BoardStateController {

    private final BoardStateService boardStateService;

    public BoardStateController(BoardStateService boardStateService) {
        this.boardStateService = boardStateService;
    }

    @GetMapping("/state")
    public ResponseEntity<Board> getState() {
        Board state = boardStateService.getState();

        return new ResponseEntity<>(state, HttpStatus.OK);
    }
}
