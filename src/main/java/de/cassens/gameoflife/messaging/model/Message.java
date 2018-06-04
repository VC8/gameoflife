package de.cassens.gameoflife.messaging.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.cassens.gameoflife.messaging.MessageDeserializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@JsonDeserialize(using = MessageDeserializer.class)
public class Message<T> {
    MessageType messageType;
    T payload;
}
