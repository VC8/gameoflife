package de.cassens.gameoflife.messaging;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import static java.text.MessageFormat.format;

@Component
public class ByteMessageConverter {
    public String createStringLogMessage(byte[] messageBytes) throws UnsupportedEncodingException {
        final String message = new String(messageBytes, "UTF-8");
        return format("Received ''{0}''", message);
    }
}
