package de.cassens.gameoflife.board.controller.create;

import de.cassens.gameoflife.board.controller.ApiMapping;
import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.board.service.create.CreateBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiMapping
public class CreateBoardController {

    private final CreateBoardService createBoardService;

    @Autowired
    public CreateBoardController(CreateBoardService createBoardService) {
        this.createBoardService = createBoardService;
    }

    @PostMapping("/create")
    public ResponseEntity createBoard(@RequestBody BoardPayload boardPayload) {
        int rows = boardPayload.getRows();
        int cols = boardPayload.getCols();

        createBoardService.createBoard(rows, cols);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
