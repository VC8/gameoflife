package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Connection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = RabbitMqTestConfig.class)
public class MessageBrokerConnectionServiceTest {

    @Autowired
    private MessageBrokerConnectionService messageBrokerConnectionService;

    @Test
    public void shouldGetConnection() {
        // given

        // when
        final Connection connection = messageBrokerConnectionService.getConnection();

        // then
        assertThat(connection, is(notNullValue()));
    }
}