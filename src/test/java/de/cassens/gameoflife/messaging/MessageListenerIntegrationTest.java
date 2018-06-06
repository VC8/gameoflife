package de.cassens.gameoflife.messaging;

import de.cassens.gameoflife.messaging.model.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MessageListenerIntegrationTestConfig.class)
public class MessageListenerIntegrationTest {

    @Autowired
    private MessageListener messageListener;
    @Autowired
    private MessageSender messageSender;

    @Test
    public void shouldReceiveMessages() throws IOException, InterruptedException {
        // given
        messageSender.sendEventMessage(EventType.INCREMENTED);

        // when

        // then
//        assertThat();
    }
}