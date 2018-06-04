package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class RabbitMqTestConfig {

    @Bean
    public ConnectionFactory rabbitMqConnectionFactory() throws IOException, TimeoutException {
        final ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        when(connectionFactory.newConnection()).thenReturn(mock(Connection.class));
        return connectionFactory;
    }

    @Bean
    public MessageBrokerConnectionService messageBrokerConnectionService(ConnectionFactory rabbitMqConnectionFactory) {
        return new MessageBrokerConnectionService(rabbitMqConnectionFactory);
    }
}
