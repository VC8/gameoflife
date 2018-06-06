package de.cassens.gameoflife.messaging.model.type;

public enum  EventType {
    CREATED("CREATED"),
    INCREMENTED("INCREMENTED"),
    DECREMENTED("DECREMENTED");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
