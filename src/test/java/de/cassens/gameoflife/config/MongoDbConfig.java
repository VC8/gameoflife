package de.cassens.gameoflife.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoDbConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String mongoDbHost;

    @Override
    public MongoClient mongoClient() {
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().socketTimeout(10000).build();
        return new MongoClient(mongoDbHost, mongoClientOptions);
    }

    @Override
    protected String getDatabaseName() {
        return "game_of_life";
    }
}
