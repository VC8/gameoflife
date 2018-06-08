package de.cassens.gameoflife.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.board.model.payload.BoardPayload;
import de.cassens.gameoflife.board.model.payload.BoardPayloadFactory;
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

    private static final String MESSAGE_TYPE = "messageType";
    private static final String PAYLOAD = "payload";
    private static final String GENERATION = "generation";
    private static final String CELLS = "cells";
    private static final String ROW = "row";
    private static final String COL = "col";
    private static final String IS_ALIVE = "alive";
    private static final String EVENT_TYPE = "eventType";
    private static final String COMMAND_TYPE = "commandType";
    private static final String BOARD_PAYLOAD = "boardPayload";
    private static final String ROWS = "rows";
    private static final String COLS = "cols";

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
            final MessageType messageType = MessageType.valueOf(message.getString(MESSAGE_TYPE));

            switch (messageType) {
                case EVENT:
                    return createEventMessage(message);
                case COMMAND:
                    return createCommandMessage(message);
                case DOCUMENT:
                    return createDocumentMessage(message);
                default:
                    throw new IOException();
            }

        } catch (Exception ex) {
            throw new IllegalStateException("mapping failed", ex);
        }
    }

    private Message createEventMessage(JSONObject message) throws JSONException {
        final EventType eventType = EventType.valueOf(message.getString(EVENT_TYPE));
        return messageFactory.createEventMessage(eventType);
    }

    private Message createCommandMessage(JSONObject message) throws JSONException {
        final CommandType commandType = CommandType.valueOf(message.getString(COMMAND_TYPE));
        if (CommandType.CREATE.equals(commandType)) {
            final JSONObject boardPayloadJson = message.getJSONObject(BOARD_PAYLOAD);
            final int rows = boardPayloadJson.getInt(ROWS);
            final int cols = boardPayloadJson.getInt(COLS);
            final BoardPayload boardPayload = BoardPayloadFactory.createBoardPayload(rows, cols);
            return messageFactory.createCommandMessage(commandType, boardPayload);
        }
        return messageFactory.createCommandMessage(commandType);
    }

    private Message createDocumentMessage(JSONObject message) throws JSONException {
        final JSONObject payload = message.getJSONObject(PAYLOAD);
        final int generation = payload.getInt(GENERATION);
        final JSONArray cellsJson = payload.getJSONArray(CELLS);
        final Cell[][] cells = convertCellsJsonToCells(cellsJson);
        final Board board = new Board(cells, generation);
        return messageFactory.createDocumentMessage(board);
    }

    private Cell[][] convertCellsJsonToCells(JSONArray cellsJson) throws JSONException {
        final List<Cell[]> cellList = new ArrayList<>();
        for (int i = 0; i < cellsJson.length(); i++) {
            final List<Cell> rowList = new ArrayList<>();
            final JSONArray cellRowJson = cellsJson.getJSONArray(i);

            for (int j = 0; j < cellRowJson.length(); j++) {
                final JSONObject cellJson = cellRowJson.getJSONObject(j);
                final int row = cellJson.getInt(ROW);
                final int col = cellJson.getInt(COL);
                final boolean isAlive = cellJson.getBoolean(IS_ALIVE);
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
