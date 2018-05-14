package de.cassens.gameoflife;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static java.lang.Integer.parseInt;

@SpringBootApplication
public class TheGameOfLifeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(TheGameOfLifeApplication.class, args);
        configurableApplicationContext.close();
    }

    @Override
    public void run(String... args) throws Exception {
        int rows = parseInt(args[0]);
        int cols = parseInt(args[1]);
        int generations = parseInt(args[2]);

        Board board = new Board(rows, cols);
        board.printBoard();

        for (int i = 0; i < generations; i++) {
            board.nextGeneration();
            board.printBoard();
        }
    }
}
