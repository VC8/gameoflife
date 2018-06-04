package de.cassens.gameoflife.messaging.model;

import lombok.Value;

@Value
public class Message<T> {
    MessageType messageType;
    T payload;
}
