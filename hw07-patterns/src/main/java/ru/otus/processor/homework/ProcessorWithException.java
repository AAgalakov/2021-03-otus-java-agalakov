package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorWithException implements Processor {

    @Override
    public Message process(Message message) {
        if (LocalDateTime.now().getSecond() % 2 == 0){
            throw new TimeException("Секунда чётная");
        }
        return message;
    }


}
