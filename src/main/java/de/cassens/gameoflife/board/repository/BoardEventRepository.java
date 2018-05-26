package de.cassens.gameoflife.board.repository;

import de.cassens.gameoflife.board.event.BoardEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BoardEventRepository extends MongoRepository<BoardEvent, UUID> {
}
