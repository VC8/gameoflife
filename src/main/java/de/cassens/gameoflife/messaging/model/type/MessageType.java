package de.cassens.gameoflife.messaging.model.type;

public enum MessageType {
    COMMAND("COMMAND"),
    EVENT("EVENT"),
    DOCUMENT("DOCUMENT");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
