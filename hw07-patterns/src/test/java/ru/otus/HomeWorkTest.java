package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorWithException;
import ru.otus.processor.homework.TimeException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class HomeWorkTest {

    @Test
    @DisplayName("Проверка выбрасывания исключения в чётную секунду")
    void failTest() {
        //given
        final var message = new Message.Builder(1L).field1("field1").build();

        //when
        final var processorWithException = new ProcessorWithException(() -> LocalDateTime
        .of(2021, 1, 1, 0, 0, 2));

        //then
        assertThatExceptionOfType(TimeException.class).isThrownBy(() -> processorWithException.process(message));
    }
}