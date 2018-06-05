package de.cassens.gameoflife.messaging.model;

public enum CommandType {
    CREATE("CREATE"),
    INCREMENT("INCREMENT"),
    DECREMENT("DECREMENT");

    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
