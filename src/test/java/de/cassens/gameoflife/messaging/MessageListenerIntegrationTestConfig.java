package de.cassens.gameoflife.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import de.cassens.gameoflife.messaging.model.MessageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MessageListenerIntegrationTestConfig {

    @Value("${rabbitmq.host}")
    private String host;

    @Bean
    public MessageListener messageListener(Channel channel) throws IOException {
        return new MessageListener(channel);
    }

    @Bean
    public MessageSender messageSender(Channel channel, MessageFactory messageFactory, MessageConverter messageConverter) {
        return new MessageSender(channel, messageFactory, messageConverter);
    }

    @Bean
    public MessageFactory messageFactory() {
        return new MessageFactory();
    }

    @Bean
    public MessageConverter messageConverter(MessageFactory messageFactory){
        return new MessageConverter(messageFactory);
    }

    @Bean
    public Channel channel(MessageBrokerChannelService messageBrokerChannelService) {
        return messageBrokerChannelService.getChannel();
    }

    @Bean
    public MessageBrokerChannelService messageBrokerChannelService(MessageBrokerConnectionService messageBrokerConnectionService){
        return new MessageBrokerChannelService(messageBrokerConnectionService);
    }

    @Bean
    public MessageBrokerConnectionService messageBrokerConnectionService(ConnectionFactory rabbitMqConnectionFactory){
        return new MessageBrokerConnectionService(rabbitMqConnectionFactory);
    }

    @Bean
    public ConnectionFactory rabbitMqConnectionFactory() {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        return connectionFactory;
    }
}
