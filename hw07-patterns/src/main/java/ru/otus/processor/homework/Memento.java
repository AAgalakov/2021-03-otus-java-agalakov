package ru.otus.processor.homework;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class Memento {
    private final Message message;
    private final LocalDateTime createdAt;

    Memento(Message message, LocalDateTime createdAt) {
        this.message = message.toBuilder().build();
        this.createdAt = createdAt;
    }

    Message getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
