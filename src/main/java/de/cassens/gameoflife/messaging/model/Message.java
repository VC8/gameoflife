package de.cassens.gameoflife.messaging.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Message<T> {
    MessageType messageType;
    T payload;
}
