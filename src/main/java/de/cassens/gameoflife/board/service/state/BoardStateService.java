package de.cassens.gameoflife.board.service.state;

import de.cassens.gameoflife.board.model.event.BoardEvent;
import de.cassens.gameoflife.board.model.Board;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Service;

@Service
public class BoardStateService extends AbstractMongoEventListener<BoardEvent> {

    private Board state;

    public Board getState() {
        if (this.state == null) throw new IllegalStateException("no board is created");
        return this.state;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<BoardEvent> event) {
        BoardEvent source = event.getSource();
        this.state = new Board(source.getCells(), source.getGeneration());
    }
}
