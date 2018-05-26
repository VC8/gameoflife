package de.cassens.gameoflife.board.controller;

import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.service.state.BoardStateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gameoflife/board")
public class BoardStateController {

    private BoardStateService boardStateService;

    public BoardStateController(BoardStateService boardStateService) {
        this.boardStateService = boardStateService;
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/state")
    public ResponseEntity<Board> getState() {
        Board state = boardStateService.getState();

        return new ResponseEntity<>(state, HttpStatus.OK);
    }
}
