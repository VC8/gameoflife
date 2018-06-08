package de.cassens.gameoflife.messaging;

import de.cassens.gameoflife.messaging.model.type.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MessageListenerIntegrationTestConfig.class)
public class MessageListenerIntegrationTest {

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