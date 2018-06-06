package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import de.cassens.gameoflife.messaging.config.Queues;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MessageBrokerChannelService {

    private Channel channel;
    private final MessageBrokerConnectionService messageBrokerConnectionService;

    public MessageBrokerChannelService(MessageBrokerConnectionService messageBrokerConnectionService) {
        this.messageBrokerConnectionService = messageBrokerConnectionService;
    }

    @PostConstruct
    public void init() throws IOException {
        final Connection connection = messageBrokerConnectionService.getConnection();
        this.channel = connection.createChannel();
        this.channel.queueDeclare(Queues.BOARD_EVENTS.getQueueName(), false, false, false, null);
    }

    @PreDestroy
    public void destroy() throws IOException, TimeoutException {
        this.channel.close();
    }

    public Channel getChannel() {
        return channel;
    }
}
