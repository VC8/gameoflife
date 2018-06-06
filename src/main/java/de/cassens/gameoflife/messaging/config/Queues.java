package de.cassens.gameoflife.messaging.config;

public enum Queues {
    BOARD_EVENTS("BOARD_EVENTS");

    private final String queueName;

    Queues(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }
}
