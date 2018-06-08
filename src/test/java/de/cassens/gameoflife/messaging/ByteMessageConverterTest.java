package de.cassens.gameoflife.messaging;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ByteMessageConverterTest {

    private final ByteMessageConverter byteMessageConverter = new ByteMessageConverter();

    @Test
    public void shouldConvertByteMessageToStringMessage() throws IOException {
        // given
        final String eventMessage = getJson("event-message.json");
        final byte[] eventMessageBytes = eventMessage.getBytes();

        // when
        String logMessage = byteMessageConverter.createStringLogMessage(eventMessageBytes);


        // then
        assertThat(logMessage, is("Received '{\"messageType\":\"EVENT\",\"eventType\":\"CREATED\"}'"));
    }

    private String getJson(String file) throws IOException {
        final Stream<String> lines = Files.lines(Paths.get("src/test/resources/" + file));
        final Optional<String> optional = lines.findFirst();
        return optional.orElse("");
    }
}