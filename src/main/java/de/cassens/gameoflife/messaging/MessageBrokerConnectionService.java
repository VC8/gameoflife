package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MessageBrokerConnectionService {

    private Connection connection;

    private final ConnectionFactory rabbitMqConnectionFactory;

    public MessageBrokerConnectionService(ConnectionFactory rabbitMqConnectionFactory) {
        this.rabbitMqConnectionFactory = rabbitMqConnectionFactory;
    }

    @PostConstruct
    public void init() {
        try {
            this.connection = rabbitMqConnectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            this.connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
