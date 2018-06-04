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
    private final MessageBrokerChannelService messageBrokerChannelService;

    public MessageSender(MessageBrokerChannelService messageBrokerChannelService) {
        this.messageBrokerChannelService = messageBrokerChannelService;
    }

    public Completable sendMessage() {
        return Observable.create(
                source -> {
                    final Channel channel = messageBrokerChannelService.getChannel();

                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    String message = "Hello Board";
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                    System.out.println("[x] Sent '" + message + "'");
                }
        ).ignoreElements();
    }

    // TODO send created event message
    // TODO send incremented event message
    // TODO send decremented event message
    // TODO send state document message
}
