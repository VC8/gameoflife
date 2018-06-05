package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import de.cassens.gameoflife.messaging.model.EventType;
import de.cassens.gameoflife.messaging.model.Message;
import de.cassens.gameoflife.messaging.model.MessageFactory;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageSenderTest {

    private final MessageBrokerChannelService messageBrokerChannelService = mock(MessageBrokerChannelService.class);
    private final MessageFactory messageFactory = mock(MessageFactory.class);
    private final MessageConverter messageConverter = mock(MessageConverter.class);
    private final MessageSender messageSender = new MessageSender(messageBrokerChannelService, messageFactory, messageConverter);

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendCreatedEventMessage() throws IOException {
        // given
        final Channel channel = givenChannel();
        final Message message = mock(Message.class);
        when(messageFactory.createEventMessage(EventType.CREATED)).thenReturn(message);
        final String eventJson = "{\"messageType\":\"EVENT\",\"payload\":\"CREATED\"}";
        when(messageConverter.convertToJsonString(message)).thenReturn(eventJson);

        // when
        messageSender.sendCreatedEventMessage();

        // then
        verify(channel).queueDeclare("BOARD_EVENTS", false, false, false, null);
        verify(channel).basicPublish("", "BOARD_EVENTS", null, eventJson.getBytes());
    }

    @Test
    public void shouldSendIncrementedEventMessage() throws IOException {
        // given
        final Channel channel = givenChannel();

        // when
        messageSender.sendIncrementedEventMessage();

        // then
        assertMessageSend(channel);
    }

    private Channel givenChannel() {
        final Channel channel = mock(Channel.class);
        when(messageBrokerChannelService.getChannel()).thenReturn(channel);
        return channel;
    }

    private void assertMessageSend(Channel channel) throws IOException {
        verify(channel).queueDeclare("BOARD_EVENTS", false, false, false, null);
        verify(channel).basicPublish(eq(""), eq("BOARD_EVENTS"), isNull(), any(byte[].class));
    }

    // TODO send incremented event message
    // TODO send decremented event message
    // TODO send state document message
}