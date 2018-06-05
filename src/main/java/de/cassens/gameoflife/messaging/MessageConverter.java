package de.cassens.gameoflife.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.cell.model.Cell;
import de.cassens.gameoflife.cell.model.CellFactory;
import de.cassens.gameoflife.messaging.model.*;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MessageFactory messageFactory;

    public MessageConverter(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public String convertToJsonString(Message documentMessage) {
        try {
            return objectMapper.writeValueAsString(documentMessage);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("mapping failed", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public Message convertToMessage(String messageJson) {
        try {
            final JSONObject message = new JSONObject(messageJson);
            final MessageType messageType = MessageType.valueOf(message.getString(MESSAGE_TYPE));

            switch (messageType) {
                case EVENT:
                    final EventType eventType = EventType.valueOf(message.getString(PAYLOAD));
                    return messageFactory.createEventMessage(eventType);
                case COMMAND:
                    final CommandType commandType = CommandType.valueOf(message.getString(PAYLOAD));
                    return messageFactory.createCommandMessage(commandType);
                case DOCUMENT:
                    final JSONObject payload = message.getJSONObject(PAYLOAD);
                    final int generation = payload.getInt(GENERATION);
                    final Cell[][] cells = convertPayloadToCells(payload);
                    final Board board = new Board(cells, generation);
                    return messageFactory.createDocumentMessage(board);
                default:
                    throw new IOException();
            }

        } catch (JSONException | IOException ex) {
            throw new IllegalStateException("mapping failed", ex);
        }
    }

    private Cell[][] convertPayloadToCells(JSONObject payload) throws JSONException {
        final JSONArray cellsJson = payload.getJSONArray(CELLS);

        final List<Cell[]> cellList = new ArrayList<>();
        for (int i = 0; i < cellsJson.length(); i++) {
            final List<Cell> rowList = new ArrayList<>();
            final JSONArray cellRowJson = cellsJson.getJSONArray(i);

            for (int j = 0; j < cellRowJson.length(); j++) {
                final JSONObject cellJson = cellRowJson.getJSONObject(j);
                final int row = cellJson.getInt("row");
                final int col = cellJson.getInt("col");
                final boolean alive = cellJson.getBoolean("alive");
                final Cell cell = CellFactory.createCell(row, col, alive);
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
