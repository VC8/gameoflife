package de.cassens.gameoflife.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.cell.model.CellFactory;
import de.cassens.gameoflife.messaging.model.message.Message;
import de.cassens.gameoflife.messaging.model.message.MessageFactory;
import de.cassens.gameoflife.messaging.model.type.CommandType;
import de.cassens.gameoflife.messaging.model.type.EventType;
import de.cassens.gameoflife.messaging.model.type.MessageType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageConverter {

    private static final String MESSAGE_TYPE_KEY = "messageType";
    private static final String PAYLOAD_KEY = "payload";
    private static final String GENERATION_KEY = "generation";
    private static final String CELLS_KEY = "cells";
    private static final String ROW_KEY = "row";
    private static final String COL_KEY = "col";
    private static final String IS_ALIVE_KEY = "alive";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MessageFactory messageFactory;

    public MessageConverter(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public String convertToJsonString(Message message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("mapping failed", ex);
        }
    }

    public Message convertToMessage(String messageJson) {
        try {
            final JSONObject message = new JSONObject(messageJson);
            final MessageType messageType = MessageType.valueOf(message.getString(MESSAGE_TYPE_KEY));

            switch (messageType) {
                case EVENT:
                    final EventType eventType = EventType.valueOf(message.getString(PAYLOAD_KEY));
                    return messageFactory.createEventMessage(eventType);
                case COMMAND:
                    final CommandType commandType = CommandType.valueOf(message.getString(PAYLOAD_KEY));
                    return messageFactory.createCommandMessage(commandType);
                case DOCUMENT:
                    final JSONObject payload = message.getJSONObject(PAYLOAD_KEY);
                    final int generation = payload.getInt(GENERATION_KEY);
                    final JSONArray cellsJson = payload.getJSONArray(CELLS_KEY);
                    final Cell[][] cells = convertCellsJsonToCells(cellsJson);
                    final Board board = new Board(cells, generation);
                    return messageFactory.createDocumentMessage(board);
                default:
                    throw new IOException();
            }

        } catch (Exception ex) {
            throw new IllegalStateException("mapping failed", ex);
        }
    }

    private Cell[][] convertCellsJsonToCells(JSONArray cellsJson) throws JSONException {
        final List<Cell[]> cellList = new ArrayList<>();
        for (int i = 0; i < cellsJson.length(); i++) {
            final List<Cell> rowList = new ArrayList<>();
            final JSONArray cellRowJson = cellsJson.getJSONArray(i);

            for (int j = 0; j < cellRowJson.length(); j++) {
                final JSONObject cellJson = cellRowJson.getJSONObject(j);
                final int row = cellJson.getInt(ROW_KEY);
                final int col = cellJson.getInt(COL_KEY);
                final boolean isAlive = cellJson.getBoolean(IS_ALIVE_KEY);
                final Cell cell = CellFactory.createCell(row, col, isAlive);
                rowList.add(cell);
            }

            Cell[] array = {};
            final Cell[] cells = rowList.toArray(array);
            cellList.add(cells);
        }

        Cell[][] array = {};
        return cellList.toArray(array);
    }
}
