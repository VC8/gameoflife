package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import org.junit.Test;

import java.io.IOException;

import static de.cassens.gameoflife.testUtil.Json.getJson;
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
}