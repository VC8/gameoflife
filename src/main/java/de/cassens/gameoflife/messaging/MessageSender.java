package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageSender {

    private final static String QUEUE_NAME = "BOARD_EVENTS";

    public Completable sendMessage() {
        return Observable.create(
                source -> {
                    try (final Connection connection = createConnection()) {
                        try (final Channel channel = connection.createChannel()) {
                            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                            String message = "Hello Board";
                            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                            System.out.println("[x] Sent '" + message + "'");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        ).ignoreElements();
    }

    // TODO Connection Service
    private Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory.newConnection();
    }
}
