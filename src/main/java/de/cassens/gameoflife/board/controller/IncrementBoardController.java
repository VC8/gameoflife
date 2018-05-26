package de.cassens.gameoflife.board.controller;

import de.cassens.gameoflife.board.service.increment.IncrementBoardCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gameoflife/board")
public class IncrementBoardController {

    private IncrementBoardCycleService incrementBoardCycleService;

    @Autowired
    public IncrementBoardController(IncrementBoardCycleService incrementBoardCycleService) {
        this.incrementBoardCycleService = incrementBoardCycleService;
    }

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/increment")
    public ResponseEntity incrementBoardCycle() {
        incrementBoardCycleService.incrementBoardCycle();

        return new ResponseEntity(HttpStatus.OK);
    }
}
