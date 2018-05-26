package de.cassens.gameoflife;

import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TheGameOfLifeApplication extends AbstractMongoConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(TheGameOfLifeApplication.class, args);
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Override
    protected String getDatabaseName() {
        return "game_of_life";
    }
}
