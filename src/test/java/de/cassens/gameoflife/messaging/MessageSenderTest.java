package de.cassens.gameoflife.messaging;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class MessageSenderTest {

    private final MessageSender messageSender = new MessageSender();

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