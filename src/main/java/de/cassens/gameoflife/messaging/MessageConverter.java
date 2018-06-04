package de.cassens.gameoflife.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.cassens.gameoflife.board.model.Board;
import de.cassens.gameoflife.messaging.model.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convertToJsonString(Message<Board> documentMessage) {
        try {
            return objectMapper.writeValueAsString(documentMessage);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("mapping failed", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public Message convertToMessage(String messageJson) {
        try {
            return objectMapper.readValue(messageJson, Message.class);
        } catch (IOException ex) {
            throw new IllegalStateException("mapping failed", ex);
        }
    }
}
