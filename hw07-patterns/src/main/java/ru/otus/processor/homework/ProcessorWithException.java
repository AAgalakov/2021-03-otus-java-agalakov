package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorWithException implements Processor {

    private final LocalDateTime localDateTime;

    public ProcessorWithException(final LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public Message process(final Message message) {
        if (localDateTime.getSecond() % 2 == 0){
            throw new TimeException("Секунда чётная");
        }
        return message;
    }
}
