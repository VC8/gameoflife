package de.cassens.gameoflife.messaging.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import de.cassens.gameoflife.messaging.MessageBrokerChannelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.host}")
    private String host;

    @Bean
    public ConnectionFactory rabbitMqConnectionFactory() {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        return connectionFactory;
    }

    @Bean
    public Channel channel(MessageBrokerChannelService messageBrokerChannelService) {
        return messageBrokerChannelService.getChannel();
    }
}
