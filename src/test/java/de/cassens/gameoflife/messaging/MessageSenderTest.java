package de.cassens.gameoflife.messaging;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

public class MessageSenderTest {

    private final MessageBrokerChannelService messageBrokerChannelService = mock(MessageBrokerChannelService.class);
    private final MessageSender messageSender = new MessageSender(messageBrokerChannelService);

    // TODO fix
    @Test
    public void shouldSendMessage() {
        // when
        Completable messageSend = messageSender.sendMessage();
        Disposable subscribe = messageSend.subscribe();
        subscribe.dispose();

        // then
        assertThat(messageSend, is(notNullValue()));
    }
}