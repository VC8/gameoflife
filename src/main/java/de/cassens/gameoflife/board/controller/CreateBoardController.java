package de.cassens.gameoflife.board.controller;

import de.cassens.gameoflife.board.model.BoardPayload;
import de.cassens.gameoflife.board.service.create.CreateBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/gameoflife/board")
public class CreateBoardController {

    private CreateBoardService createBoardService;

    @Autowired
    public CreateBoardController(CreateBoardService createBoardService) {
        this.createBoardService = createBoardService;
    }

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity createBoard(@RequestBody BoardPayload boardPayload) {
        int rows = boardPayload.getRows();
        int cols = boardPayload.getCols();

        createBoardService.createBoard(rows, cols);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
