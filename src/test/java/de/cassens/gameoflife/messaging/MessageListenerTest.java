package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import de.cassens.gameoflife.messaging.model.EventType;
import de.cassens.gameoflife.messaging.model.Message;
import de.cassens.gameoflife.messaging.model.MessageFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class MessageListenerTest {

    private final Channel channel = mock(Channel.class);
    private final MessageListener messageListener = new MessageListener(channel);

    public MessageListenerTest() throws IOException {
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldDispatchMessage() throws IOException {
        // given
        final String eventMessage = getJson("event-message.json");

        // when
        messageListener.handleDelivery(null, null, null, eventMessage.getBytes());

        // then
//        assertThat();
    }

    private String getJson(String file) throws IOException {
        final Stream<String> lines = Files.lines(Paths.get("src/test/resources/" + file));
        final Optional<String> optional = lines.findFirst();
        return optional.orElse("");
    }
}