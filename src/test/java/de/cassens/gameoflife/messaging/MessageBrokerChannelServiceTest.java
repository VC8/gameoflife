package de.cassens.gameoflife.messaging;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class MessageBrokerChannelServiceTest {

    private final MessageBrokerConnectionService messageBrokerConnectionService = mock(MessageBrokerConnectionService.class);
    private final MessageBrokerChannelService messageBrokerChannelService = new MessageBrokerChannelService(messageBrokerConnectionService);

    @Test
    public void shouldGetChannel() throws IOException {
        // given
        givenMessageBrokerConnectionService();
        messageBrokerChannelService.init();

        // when
        final Channel channel = messageBrokerChannelService.getChannel();

        // then
        assertThat(channel, is(notNullValue()));
    }

    private void givenMessageBrokerConnectionService() throws IOException {
        final Connection connection = mock(Connection.class);
        when(connection.createChannel()).thenReturn(mock(Channel.class));
        when(messageBrokerConnectionService.getConnection()).thenReturn(connection);
    }
}