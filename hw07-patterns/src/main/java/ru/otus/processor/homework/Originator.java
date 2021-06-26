package ru.otus.processor.homework;

import ru.otus.model.Message;

import java.util.ArrayDeque;
import java.util.Deque;

public class Originator {
    private final Deque<Memento> stack = new ArrayDeque<>();

    private final DateTimeProvider dateTimeProvider;

    public Originator(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public void saveState(Message message) {
        stack.push(new Memento(message, dateTimeProvider.getDate()));
    }

    public Message restoreState() {
        var memento = stack.pop();
        System.out.println("createdAt:" + memento.getCreatedAt());
        return memento.getMessage();
    }
}
