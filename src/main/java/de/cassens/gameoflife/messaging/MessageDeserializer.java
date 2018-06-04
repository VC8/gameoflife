package de.cassens.gameoflife.messaging;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.cell.model.CellFactory;
import de.cassens.gameoflife.messaging.model.CommandType;
import de.cassens.gameoflife.messaging.model.EventType;
import de.cassens.gameoflife.messaging.model.Message;
import de.cassens.gameoflife.messaging.model.MessageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageDeserializer extends StdDeserializer<Message> {

    public MessageDeserializer() {
        super(Message.class);
    }

    @Override
    public Message deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final TreeNode message = jsonParser.readValueAsTree();
        String messageType = message.get("messageType").toString();
        messageType = messageType.replace("\"", "");

        MessageFactory messageFactory = new MessageFactory();
        String payload = message.get("payload").toString();
        payload = payload.replace("\"", "");

        switch (messageType) {
            case "COMMAND":
                switch (payload) {
                    case "CREATE":
                        return messageFactory.createCommandMessage(CommandType.CREATE);
                    case "INCREMENT":
                        return messageFactory.createCommandMessage(CommandType.INCREMENT);
                    case "DECREMENT":
                        return messageFactory.createCommandMessage(CommandType.DECREMENT);
                    default:
                        throw new IOException();
                }
            case "EVENT":
                switch (payload) {
                    case "CREATED":
                        return messageFactory.createEventMessage(EventType.CREATED);
                    case "INCREMENTED":
                        return messageFactory.createEventMessage(EventType.INCREMENTED);
                    case "DECREMENTED":
                        return messageFactory.createEventMessage(EventType.DECREMENTED);
                    default:
                        throw new IOException();
                }
            case "DOCUMENT":
                final TreeNode payloadTree = message.get("payload");
                final ObjectNode objectNode = (ObjectNode) payloadTree;
                final Iterator<JsonNode> elements = objectNode.elements();

                int generation = 0;
                final List<Cell[]> cellList = new ArrayList<>();
                while (elements.hasNext()) {
                    final JsonNode jsonNode = elements.next();
                    if (jsonNode.canConvertToInt()) generation = jsonNode.asInt();
                    // parse cells
                    else {
                        final Iterator<JsonNode> rows = jsonNode.elements();

                        // parse rows
                        while (rows.hasNext()) {
                            final Iterator<JsonNode> cols = rows.next().elements();
                            final List<Cell> rowList = new ArrayList<>();

                            // parse cells
                            while (cols.hasNext()) {
                                final JsonNode entry = cols.next();
                                final int row = entry.get("row").asInt();
                                final int col = entry.get("col").asInt();
                                final boolean alive = entry.get("alive").asBoolean();
                                final Cell cell = CellFactory.createCell(row, col, alive);
                                rowList.add(cell);
                            }

                            Cell[] cellArray = {};
                            final Cell[] cells = rowList.toArray(cellArray);
                            cellList.add(cells);
                        }
                    }
                }

                Cell[][] cellArray = {};
                final Cell[][] cells = cellList.toArray(cellArray);

                final Board board = new Board(cells, generation);
                return messageFactory.createDocumentMessage(board);
            default:
                throw new IOException();
        }
    }
}
