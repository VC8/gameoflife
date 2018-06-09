package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import de.cassens.gameoflife.messaging.model.message.CommandMessage;
import org.junit.Test;

import java.io.IOException;

import static de.cassens.gameoflife.testUtil.Json.getJson;
import static org.mockito.Mockito.*;

public class MessageListenerTest {

    private final Channel channel = mock(Channel.class);
    private final MessageConverter messageConverter = mock(MessageConverter.class);
    private final CommandDispatcher commandDispatcher = mock(CommandDispatcher.class);
    private final MessageListener messageListener = new MessageListener(channel, messageConverter, commandDispatcher);

    public MessageListenerTest() throws IOException {
    }

    @Test
    public void shouldDispatchCommandMessage() throws IOException {
        // given
        final String eventMessage = getJson("command-message.json");
        final CommandMessage commandMessage = mock(CommandMessage.class);
        when(messageConverter.convertToMessage(eventMessage)).thenReturn(commandMessage);

        // when
        messageListener.handleDelivery(null, null, null, eventMessage.getBytes());

        // then
        verify(commandDispatcher).dispatchCommandFromMessage(commandMessage);
    }
}