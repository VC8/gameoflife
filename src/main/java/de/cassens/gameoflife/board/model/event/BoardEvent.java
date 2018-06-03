package de.cassens.gameoflife.board.model.event;

import de.cassens.gameoflife.cell.model.Cell;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Value
@NonFinal
@Document(collection = "board_events")
public class BoardEvent implements Serializable {
    @Id UUID eventId;
    Date timestamp;
    BoardEventType boardEventType;
    int generation;
    Cell[][] cells;
}
