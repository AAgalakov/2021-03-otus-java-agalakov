package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorWithException implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorWithException(final DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(final Message message) {
        if (dateTimeProvider.getLocalDateTime().getSecond() % 2 == 0){
            throw new TimeException("Секунда чётная");
        }
        return message;
    }
}
